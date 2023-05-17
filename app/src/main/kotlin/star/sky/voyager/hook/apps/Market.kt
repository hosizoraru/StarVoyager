package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.market.AsWhat
import star.sky.voyager.utils.init.AppRegister

object Market : AppRegister() {
    override val packageName: String = "com.xiaomi.market"
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            AsWhat, // 伪装机型为:
        )
    }
}