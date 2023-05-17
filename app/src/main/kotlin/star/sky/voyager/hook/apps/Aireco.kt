package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.aireco.DeviceModify
import star.sky.voyager.utils.init.AppRegister

object Aireco : AppRegister() {
    override val packageName: String = "com.xiaomi.aireco"
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            DeviceModify, // 移除限制
        )
    }
}