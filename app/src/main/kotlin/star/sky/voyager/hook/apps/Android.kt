package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.android.AllowUntrustedTouches
import star.sky.voyager.hook.hooks.android.DeleteOnPostNotification
import star.sky.voyager.hook.hooks.android.DisableFlagSecure
import star.sky.voyager.hook.hooks.android.DoNotClearAppPlusA
import star.sky.voyager.hook.hooks.android.KillDomainVerification
import star.sky.voyager.hook.hooks.android.MaxFreeFormA
import star.sky.voyager.hook.hooks.android.MaxWallpaperScale
import star.sky.voyager.hook.hooks.android.SystemPropertiesHook
import star.sky.voyager.utils.init.AppRegister

object Android : AppRegister() {
    override val packageName: String = "android"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            DisableFlagSecure,
            DeleteOnPostNotification,
            MaxWallpaperScale,
            AllowUntrustedTouches,
            KillDomainVerification,
            MaxFreeFormA,
            DoNotClearAppPlusA,
            SystemPropertiesHook,
        )
    }
}