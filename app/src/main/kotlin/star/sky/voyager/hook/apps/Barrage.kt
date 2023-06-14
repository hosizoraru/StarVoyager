package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.barrage.AnyBarrage
import star.sky.voyager.utils.init.AppRegister

object Barrage : AppRegister() {
    override val packageName: String = "com.xiaomi.barrage"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            AnyBarrage, // 弹幕通知-允许所有应用使用弹幕通知
        )
    }
}