package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object ScreenHoldOn : HookRegister() {
    override fun init() = hasEnable("screen_hold_on") {
        dexKitBridge.findMethodUsingString {
            usingString = "remove_screen_off_hold_on"
            methodReturnType = "boolean"
        }.single().getMethodInstance(classLoader).createHook {
            before {
                it.result = false
            }
        }
    }
}