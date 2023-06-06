package star.sky.voyager.hook.hooks.personalassistant

import android.view.Window
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.api.getValueByFields
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import star.sky.voyager.utils.yife.DexKit.loadDexKit
import kotlin.math.abs

object BlurPersonalAssistant : HookRegister() {
    override fun init() = hasEnable("blur_personal_assistant") {
        val blurRadius = getInt("blur_personal_assistant_radius", 80)
        var lastBlurRadius = -1
        loadDexKit()
        dexKitBridge.batchFindMethodsUsingStrings {
            addQuery("qwq", listOf("ScrollStateManager", "Manager must be init before using"))
            matchType = MatchType.FULL
        }.forEach { (_, methods) ->
            methods.single()
                .getMethodInstance(classLoader).createHook {
                    after { param ->
                        val scrollX = param.args[0] as Float
                        val fieldNames = ('a'..'z').map { it.toString() }
                        val window = getValueByFields(param.thisObject, fieldNames) ?: return@after
                        if (window.javaClass.name.contains("Window")) {
                            window as Window
                            val blurRadius1 = (scrollX * blurRadius).toInt()
                            if (abs(blurRadius1 - lastBlurRadius) > 2) {
                                window.setBackgroundBlurRadius(blurRadius1)
                                lastBlurRadius = blurRadius1
                            }
                        }
                    }
                }
        }
    }
}