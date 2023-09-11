package star.sky.voyager.hook.hooks.personalassistant

import android.view.Window
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.BlurDraw.getValueByFields
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import kotlin.math.abs

object BlurPersonalAssistant : HookRegister() {
    private val blurRadius by lazy {
        getInt("blur_personal_assistant_radius", 80)
    }
    private val ScrollStateManager by lazy {
//        dexKitBridge.batchFindMethodsUsingStrings {
//            addQuery("qwq", setOf("ScrollStateManager", "Manager must be init before using"))
//            matchType = MatchType.FULL
//        }.firstNotNullOf { (_, methods) -> methods.firstOrNull() }.getMethodInstance(classLoader)
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals(
                    "ScrollStateManager",
                    "Manager must be init before using"
                )
            }
        }.firstOrNull()?.getMethodInstance(classLoader)
    }

    override fun init() = hasEnable("blur_personal_assistant") {
        var lastBlurRadius = -1
        ScrollStateManager?.createHook {
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