package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.cast.ForceSupportSendApp
import star.sky.voyager.utils.init.AppRegister

object Cast : AppRegister() {
    override val packageName: String = "com.milink.service"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            ForceSupportSendApp, // 强制所有应用支持在另一个设备打开
        )
    }
}