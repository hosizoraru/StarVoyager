package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.multipackage.BatteryStyle
import star.sky.voyager.hook.hooks.systemui.BatteryPercentage
import star.sky.voyager.hook.hooks.systemui.CanNotificationSlide
import star.sky.voyager.hook.hooks.systemui.ControlCenterMod
import star.sky.voyager.hook.hooks.systemui.ControlCenterStyle
import star.sky.voyager.hook.hooks.systemui.CustomMobileTypeText
import star.sky.voyager.hook.hooks.systemui.DisableBluetooth
import star.sky.voyager.hook.hooks.systemui.DoubleLineNetworkSpeed
import star.sky.voyager.hook.hooks.systemui.HideBatteryIcon
import star.sky.voyager.hook.hooks.systemui.HideHDIcon
import star.sky.voyager.hook.hooks.systemui.HideMobileActivityIcon
import star.sky.voyager.hook.hooks.systemui.HideMobileTypeIcon
import star.sky.voyager.hook.hooks.systemui.HideNetworkSpeedSplitter
import star.sky.voyager.hook.hooks.systemui.HideSimIcon
import star.sky.voyager.hook.hooks.systemui.HideStatusBarIcon
import star.sky.voyager.hook.hooks.systemui.HideStatusBarNetworkSpeedSecond
import star.sky.voyager.hook.hooks.systemui.HideWifiActivityIcon
import star.sky.voyager.hook.hooks.systemui.IconPosition
import star.sky.voyager.hook.hooks.systemui.LockScreenBlurButton
import star.sky.voyager.hook.hooks.systemui.LockScreenClockDisplaySeconds
import star.sky.voyager.hook.hooks.systemui.LockScreenCurrent
import star.sky.voyager.hook.hooks.systemui.LockScreenDoubleTapToSleep
import star.sky.voyager.hook.hooks.systemui.LockScreenFont
import star.sky.voyager.hook.hooks.systemui.LockScreenHint
import star.sky.voyager.hook.hooks.systemui.LockScreenRemoveCamera
import star.sky.voyager.hook.hooks.systemui.LockScreenRemoveLeftSide
import star.sky.voyager.hook.hooks.systemui.LockScreenZenMode
import star.sky.voyager.hook.hooks.systemui.LockscreenChargingInfo
import star.sky.voyager.hook.hooks.systemui.MaximumNumberOfNotificationIcons
import star.sky.voyager.hook.hooks.systemui.MonetTheme
import star.sky.voyager.hook.hooks.systemui.NoPasswordHook
import star.sky.voyager.hook.hooks.systemui.NotificationIcon
import star.sky.voyager.hook.hooks.systemui.NotificationMod
import star.sky.voyager.hook.hooks.systemui.NotificationSettingsNoWhiteList
import star.sky.voyager.hook.hooks.systemui.NotificationWeather
import star.sky.voyager.hook.hooks.systemui.OldNotificationWeather
import star.sky.voyager.hook.hooks.systemui.OldQSCustom
import star.sky.voyager.hook.hooks.systemui.RedirectToNotificationChannelSetting
import star.sky.voyager.hook.hooks.systemui.RestoreNearbyTile
import star.sky.voyager.hook.hooks.systemui.ShowWifiStandard
import star.sky.voyager.hook.hooks.systemui.StatusBarBattery
import star.sky.voyager.hook.hooks.systemui.StatusBarBigMobileTypeIcon
import star.sky.voyager.hook.hooks.systemui.StatusBarDoubleTapToSleep
import star.sky.voyager.hook.hooks.systemui.StatusBarLayout
import star.sky.voyager.hook.hooks.systemui.StatusBarNetworkSpeedRefreshSpeed
import star.sky.voyager.hook.hooks.systemui.StatusBarTimeCustomization
import star.sky.voyager.hook.hooks.systemui.UseNewHD
import star.sky.voyager.hook.hooks.systemui.WaveCharge
import star.sky.voyager.utils.init.AppRegister

object SystemUI : AppRegister() {
    override val packageName: String = "com.android.systemui"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            MonetTheme, // 自定义系统主题色
            UseNewHD, // 强制使用新 HD 图标
            // 隐藏图标 Start
            HideStatusBarIcon,
            HideBatteryIcon,
            HideHDIcon,
            HideSimIcon,
            HideMobileActivityIcon,
            HideMobileTypeIcon,
            HideWifiActivityIcon,
            // 隐藏图标 End
            BatteryStyle, // 解锁全部电池样式
            ControlCenterStyle, // 解锁旧版控制中心样式
//            AodAvailable, // 解锁 AOD
            LockscreenChargingInfo, // Yife-锁屏界面电池信息
            NotificationIcon, // 通知中心 隐藏通知设置图标
            NotificationMod, // 通知中心 时间日期横屏时钟自定义
            ControlCenterMod, // 控制中心 时间日期自定义
            StatusBarDoubleTapToSleep, // 状态栏-双击锁定屏幕
            StatusBarBattery, // 状态栏显示关于电池
            StatusBarLayout, // 状态栏布局
            StatusBarTimeCustomization, // 状态栏时钟格式
            ShowWifiStandard, // 显示 WiFi 标准
            IconPosition, // 图标位置
            BatteryPercentage, // 电池百分比字体大小
            CustomMobileTypeText, // 自定义移动类型文本
            StatusBarBigMobileTypeIcon, // 大移动类型图标
            MaximumNumberOfNotificationIcons, // 通知图标/通知点数/锁屏界面通知图标最大数量
            OldNotificationWeather, // 通知中心天气
            NotificationWeather, // 通知中心天气
//            NewNotificationWeather, // XX中心天气
            CanNotificationSlide, // 允许大多数应用通知下拉展开小窗
            NotificationSettingsNoWhiteList, // 移除通知设置白名单
            RedirectToNotificationChannelSetting, // 打开通知频道设置 打开通知菜单的设置会导航至频道设置而不是应用通用通知设置
            RestoreNearbyTile, // 恢复附近分享磁贴
            DisableBluetooth, // 禁用蓝牙临时关闭
            LockScreenClockDisplaySeconds, // 时钟显示秒数
            LockScreenFont, // 锁屏界面时钟使用系统字体
            LockScreenBlurButton, // 模糊锁屏界面按钮
            LockScreenRemoveLeftSide, // 移除锁屏负一屏功能
            LockScreenRemoveCamera, // 移除锁屏相机功能
            WaveCharge, // 启用Alpha充电动画
            LockScreenCurrent, // 充电时显示当前电流
            LockScreenDoubleTapToSleep, // 锁屏-双击锁定屏幕
            LockScreenZenMode, // 隐藏锁屏界面的勿扰模式通知
            LockScreenHint, // 隐藏锁屏界面底部的解锁提示
            NoPasswordHook, // 开机免输密码
            OldQSCustom, // 自定义行列数
            // 状态栏网络速度 Start
            StatusBarNetworkSpeedRefreshSpeed,
            HideStatusBarNetworkSpeedSecond,
            HideNetworkSpeedSplitter,
            DoubleLineNetworkSpeed,
            // 状态栏网络速度 End
        )
    }
}