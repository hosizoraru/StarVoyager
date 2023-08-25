package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.phrase.GboardMi
import star.sky.voyager.utils.init.AppRegister

object Phrase : AppRegister() {
    override val packageName: String = "com.miui.phrase"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            GboardMi,
        )
    }
}