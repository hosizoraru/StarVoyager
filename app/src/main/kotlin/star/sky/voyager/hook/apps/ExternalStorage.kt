package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.externalstorage.NoStorageRestrict
import star.sky.voyager.utils.init.AppRegister

object ExternalStorage : AppRegister() {
    override val packageName: String = "com.android.externalstorage"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            NoStorageRestrict,
        )
    }
}