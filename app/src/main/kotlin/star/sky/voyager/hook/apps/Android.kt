package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.android.AllowUntrustedTouches
import star.sky.voyager.hook.hooks.android.DarkModeForAllApps
import star.sky.voyager.hook.hooks.android.DeleteOnPostNotification
import star.sky.voyager.hook.hooks.android.Disable72hVerify
import star.sky.voyager.hook.hooks.android.DisableFlagSecure
import star.sky.voyager.hook.hooks.android.DisableFlagSecureK
import star.sky.voyager.hook.hooks.android.KillDomainVerification
import star.sky.voyager.hook.hooks.android.MaxWallpaperScale
import star.sky.voyager.hook.hooks.android.RemoveSmallWindowRestrictions
import star.sky.voyager.hook.hooks.android.SystemPropertiesHook
import star.sky.voyager.hook.hooks.corepatch.CorePatchMainHook
import star.sky.voyager.hook.hooks.maxmipad.DisableFixedOrientation
import star.sky.voyager.hook.hooks.maxmipad.IgnoreStylusKeyGesture
import star.sky.voyager.hook.hooks.maxmipad.NoMagicPointer
import star.sky.voyager.hook.hooks.maxmipad.RemoveStylusBluetoothRestriction
import star.sky.voyager.hook.hooks.maxmipad.RestoreEsc
import star.sky.voyager.hook.hooks.maxmipad.SetGestureNeedFingerNumTo4
import star.sky.voyager.hook.hooks.multipackage.AospShareSheet
import star.sky.voyager.hook.hooks.multipackage.MaxFreeForm
import star.sky.voyager.utils.init.AppRegister
import star.sky.voyager.utils.key.hasEnable

object Android : AppRegister() {
    override val packageName: String = "android"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        CorePatchMainHook().handleLoadPackage(lpparam) // 核心破解
        hasEnable("disable_flag_secure") {
            DisableFlagSecureK().handleLoadPackage(lpparam) // 允许截图
        }
        autoInitHooks(
            lpparam,
//            AodAvailable,
            AospShareSheet, // 使用原生分享界面/打开方式
            DarkModeForAllApps, // 允许所有应用使用深色模式
            DisableFlagSecure, // 允许截图
            DeleteOnPostNotification, // 移除上层显示通知
            RemoveSmallWindowRestrictions, // 强制使用小窗
            MaxWallpaperScale, // 壁纸缩放比例
            AllowUntrustedTouches, // 允许不受信任的触摸
            KillDomainVerification, // 禁用域验证
            MaxFreeForm, // 解锁小窗数量限制
            Disable72hVerify, // 禁用每 72h 验证锁屏密码
//            XYVelocity,
            SystemPropertiesHook, // 媒体音量阶数
            // max mi pad
            DisableFixedOrientation, // 禁用固定屏幕方向
            IgnoreStylusKeyGesture, // 忽略触控笔按键手势
            NoMagicPointer, // 关闭Magic Pointer
            RemoveStylusBluetoothRestriction, // 去除触控笔蓝牙限制
            RestoreEsc, // 恢复ESC键功能
            SetGestureNeedFingerNumTo4, // 交换手势所需的手指数量-Android部分
            // max mi pad
        )
    }
}