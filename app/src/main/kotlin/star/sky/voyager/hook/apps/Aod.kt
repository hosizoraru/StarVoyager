package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.aod.UnlockAlwaysOnDisplay
import star.sky.voyager.utils.init.AppRegister

object Aod : AppRegister() {
    override val packageName: String = "com.miui.aod"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            UnlockAlwaysOnDisplay, // 解锁息屏显示时长限制
        )
    }
}