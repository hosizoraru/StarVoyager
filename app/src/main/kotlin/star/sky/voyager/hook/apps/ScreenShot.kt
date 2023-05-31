package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.mediaeditor.UnlockUnlimitedCropping
import star.sky.voyager.utils.init.AppRegister

object ScreenShot : AppRegister() {
    override val packageName: String = "com.miui.screenshot"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            UnlockUnlimitedCropping,
        )
    }
}