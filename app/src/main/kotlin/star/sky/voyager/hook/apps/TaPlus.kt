package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.taplus.HorizontalContentExtension
import star.sky.voyager.hook.hooks.taplus.UnlockForPad
import star.sky.voyager.utils.init.AppRegister

object TaPlus : AppRegister() {
    override val packageName: String = "com.miui.contentextension"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            HorizontalContentExtension, // 允许在横屏方向下使用传送门
            UnlockForPad, // 为Pad解锁传送门
        )
    }
}