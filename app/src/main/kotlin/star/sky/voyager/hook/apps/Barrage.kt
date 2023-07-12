package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.barrage.AnyAppBarrage
import star.sky.voyager.hook.hooks.barrage.AnyWhereBarrage
import star.sky.voyager.hook.hooks.barrage.ModifyBarrageLength
import star.sky.voyager.utils.init.AppRegister

object Barrage : AppRegister() {
    override val packageName: String = "com.xiaomi.barrage"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            AnyAppBarrage, // 弹幕通知-允许所有应用使用弹幕通知
            AnyWhereBarrage, // 弹幕通知-允许全局显示弹幕通知
            ModifyBarrageLength, // 弹幕长度
        )
    }
}