package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.multipackage.ForceSupportSendApp
import star.sky.voyager.utils.init.AppRegister

object Mirror : AppRegister() {
    override val packageName: String = "com.xiaomi.mirror"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            ForceSupportSendApp,
        )
    }
}