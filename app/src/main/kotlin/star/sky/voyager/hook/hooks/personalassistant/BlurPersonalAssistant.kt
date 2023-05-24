package star.sky.voyager.hook.hooks.personalassistant

import android.view.Window
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.api.getValueByField
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
        dexKitBridge.findMethodUsingString {
            usingString = "Manager must be init before using"
            matchType = MatchType.FULL
            methodDeclareClass = "com.miui.personalassistant.core.overlay.AssistantOverlayWindow"
        }.single().getMethodInstance(classLoader).createHook {
            after { param ->
                val scrollX = param.args[0] as Float
                val window = getValueByField(param.thisObject, "b") ?: return@after
                if (window.javaClass.name.contains("Window")) {
                    try {
                        window as Window
                        val blurRadius = (scrollX * blurRadius).toInt()
                        if (abs(blurRadius - lastBlurRadius) > 2) {
                            window.setBackgroundBlurRadius(blurRadius)
                            lastBlurRadius = blurRadius
                        }
                    } catch (_: Throwable) {
                        // None Log
                    }
                }
            }
        }
    }
}