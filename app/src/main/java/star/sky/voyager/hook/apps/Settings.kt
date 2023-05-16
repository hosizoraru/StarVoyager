package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.settings.EnableSpeedMode
import star.sky.voyager.hook.hooks.settings.QuickInstallPermission
import star.sky.voyager.hook.hooks.settings.ShowNotificationImportance
import star.sky.voyager.utils.init.AppRegister

object Settings : AppRegister() {
    override val packageName: String = "com.android.settings"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            EnableSpeedMode,
            ShowNotificationImportance,
            QuickInstallPermission,
        )
    }
}