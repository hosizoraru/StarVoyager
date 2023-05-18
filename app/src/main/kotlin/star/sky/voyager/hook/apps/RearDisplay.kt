package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.rearddisplay.RearDisplayWeather
import star.sky.voyager.utils.init.AppRegister

object RearDisplay : AppRegister() {
    override val packageName: String = "com.xiaomi.misubscreenui"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            RearDisplayWeather,
        )
    }
}