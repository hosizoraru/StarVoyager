package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.aicr.Attr
import star.sky.voyager.utils.init.AppRegister

object AiCR : AppRegister() {
    override val packageName: String = "com.xiaomi.aicr"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            Attr,
        )
    }
}