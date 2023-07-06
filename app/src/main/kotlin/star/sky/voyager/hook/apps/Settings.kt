package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.multipackage.BatteryStyle
import star.sky.voyager.hook.hooks.multipackage.TaplusUnlock
import star.sky.voyager.hook.hooks.settings.HpLocation
import star.sky.voyager.hook.hooks.settings.NeverTimeOut
import star.sky.voyager.hook.hooks.settings.NewNfcPage
import star.sky.voyager.hook.hooks.settings.NoThroughTheList
import star.sky.voyager.hook.hooks.settings.NoveltyHaptic
import star.sky.voyager.hook.hooks.settings.QuickInstallPermission
import star.sky.voyager.hook.hooks.settings.ShowNotificationImportance
import star.sky.voyager.hook.hooks.settings.SpeedMode
import star.sky.voyager.hook.hooks.settings.VipService
import star.sky.voyager.utils.init.AppRegister

object Settings : AppRegister() {
    override val packageName: String = "com.android.settings"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            BatteryStyle, // 解锁全部电池样式
            HpLocation, // 解锁高精度位置服务
            NeverTimeOut, // 为Oled机型解锁永不息屏
//            AodAvailable, // 解锁 AOD
            ShowNotificationImportance, // 显示通知重要程度
            QuickInstallPermission, // 安装未知应用权限免翻应用列表
            NoThroughTheList, // 显示在上层授权免翻应用列表
//            AppMode, // 解锁应用布局设置
            NewNfcPage, // 新版 NFC 页面
            NoveltyHaptic, // 解锁触控反馈水平
            SpeedMode, // 显示极致模式选项
            TaplusUnlock, // 解锁传送门
            VipService, // 显示我的服务
//            MultipleFreeform,
        )
    }
}