package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object ScreenHoldOn : HookRegister() {
    private val screen by lazy {
//        dexKitBridge.findMethodUsingString {
//            usingString = "remove_screen_off_hold_on"
//            methodReturnType = "boolean"
//        }.firstOrNull()?.getMethodInstance(classLoader)
        dexKitBridge.findMethod {
            matcher {
                usingStrings = listOf("remove_screen_off_hold_on")
                returnType = "boolean"
            }
        }.firstOrNull()?.getMethodInstance(classLoader)
    }

    override fun init() = hasEnable("screen_hold_on") {
        screen?.createHook {
            before {
                it.result = false
            }
        }
    }
}