package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.powerkeeper.CustomRefreshRateP
import star.sky.voyager.hook.hooks.powerkeeper.DoNotClearApp
import star.sky.voyager.hook.hooks.powerkeeper.DoNotClearAppPlusP
import star.sky.voyager.utils.init.AppRegister

object PowerKeeper : AppRegister() {
    override val packageName: String = "com.miui.powerkeeper"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            CustomRefreshRateP,
            DoNotClearApp,
            DoNotClearAppPlusP,
        )
    }
}