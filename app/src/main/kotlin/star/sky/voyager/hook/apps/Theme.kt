package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.utils.init.AppRegister

object Theme : AppRegister() {
    override val packageName: String = "com.android.thememanager"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
//            MiDevice,
        )
    }
}