package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.multipackage.AospShareSheet
import star.sky.voyager.hook.hooks.multipackage.BatteryStyle
import star.sky.voyager.hook.hooks.multipackage.DC
import star.sky.voyager.hook.hooks.multipackage.TaplusUnlock
import star.sky.voyager.hook.hooks.settings.GoogleSettings
import star.sky.voyager.hook.hooks.settings.HpLocation
import star.sky.voyager.hook.hooks.settings.ImeBottom
import star.sky.voyager.hook.hooks.settings.MechKeyboard
import star.sky.voyager.hook.hooks.settings.MiGboard
import star.sky.voyager.hook.hooks.settings.NeverTimeOut
import star.sky.voyager.hook.hooks.settings.NewNfcPage
import star.sky.voyager.hook.hooks.settings.NfcPageFix
import star.sky.voyager.hook.hooks.settings.NoThroughTheList
import star.sky.voyager.hook.hooks.settings.NoveltyHaptic
import star.sky.voyager.hook.hooks.settings.QuickInstallPermission
import star.sky.voyager.hook.hooks.settings.ShowNotificationHistoryAndLog
import star.sky.voyager.hook.hooks.settings.ShowNotificationImportance
import star.sky.voyager.hook.hooks.settings.SpeedMode
import star.sky.voyager.hook.hooks.settings.StarVoyager
import star.sky.voyager.hook.hooks.settings.VipService
import star.sky.voyager.hook.hooks.settings.VoipAssistant
import star.sky.voyager.utils.init.AppRegister

object Settings : AppRegister() {
    override val packageName: String = "com.android.settings"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            BatteryStyle, // 解锁全部电池样式
            TaplusUnlock, // 解锁传送门
            ShowNotificationImportance, // 显示通知重要程度
            QuickInstallPermission, // 安装未知应用权限免翻应用列表
            NoThroughTheList, // 显示在上层授权免翻应用列表
//            AodAvailable, // 解锁 AOD
//            AppMode, // 解锁应用布局设置
            HpLocation, // 解锁高精度位置服务
            ImeBottom, // 解锁全面屏优化
            MechKeyboard, // 解锁机械键盘
            MiGboard, // 添加Gboard为小米定制版输入法
            NeverTimeOut, // 为Oled机型解锁永不息屏
            NewNfcPage, // 新版 NFC 页面
            NoveltyHaptic, // 解锁触控反馈水平
            SpeedMode, // 显示极致模式选项
            VipService, // 显示我的服务
            VoipAssistant, // 解锁网络通话助手
            ShowNotificationHistoryAndLog, // 在通知与控制中心中显示通知历史记录与日志入口
            GoogleSettings, // 系统设置里显示 Google
            NfcPageFix,
//            MultipleFreeform,
            AospShareSheet,
            DC,
            StarVoyager,
        )
    }
}