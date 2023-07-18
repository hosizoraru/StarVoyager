package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.safeDexKitBridge

object ShowAllApp : HookRegister() {
    override fun init() = hasEnable("show_all_app_dsm") {
        safeDexKitBridge.findMethod {
            methodName = "isHideAppValid"
            methodReturnType = "boolean"
        }.map {
            it.getMethodInstance(classLoader).createHook {
                returnConstant(true)
            }
        }
    }
}