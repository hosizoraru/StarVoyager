package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.systemui.BatteryPercentage
import star.sky.voyager.hook.hooks.systemui.BlurLockScreenButton
import star.sky.voyager.hook.hooks.systemui.CanNotificationSlide
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
import star.sky.voyager.hook.hooks.systemui.LockScreenClockDisplaySeconds
import star.sky.voyager.hook.hooks.systemui.LockScreenCurrent
import star.sky.voyager.hook.hooks.systemui.LockScreenDoubleTapToSleep
import star.sky.voyager.hook.hooks.systemui.MaximumNumberOfNotificationIcons
import star.sky.voyager.hook.hooks.systemui.NoPasswordHook
import star.sky.voyager.hook.hooks.systemui.NotificationSettingsNoWhiteList
import star.sky.voyager.hook.hooks.systemui.NotificationWeather
import star.sky.voyager.hook.hooks.systemui.OldNotificationWeather
import star.sky.voyager.hook.hooks.systemui.OldQSCustom
import star.sky.voyager.hook.hooks.systemui.RemoveLockScreenCamera
import star.sky.voyager.hook.hooks.systemui.RemoveTheLeftSideOfTheLockScreen
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
            StatusBarDoubleTapToSleep, // 状态栏-双击锁定屏幕
            StatusBarBattery, // 状态栏显示关于电池
            StatusBarLayout, // 状态栏布局
            StatusBarTimeCustomization, // 状态栏时钟格式
            ShowWifiStandard, // 显示 WiFi 标准
            UseNewHD, // 强制使用新 HD 图标
            BatteryPercentage, // 电池百分比字体大小
            CustomMobileTypeText, // 自定义移动类型文本
            StatusBarBigMobileTypeIcon, // 大移动类型图标
            MaximumNumberOfNotificationIcons, // 通知图标/通知点数/锁屏界面通知图标最大数量
            OldNotificationWeather, // 通知中心天气
            NotificationWeather, // 通知中心天气
            CanNotificationSlide, // 允许大多数应用通知下拉展开小窗
            NotificationSettingsNoWhiteList, // 移除通知设置白名单
            RestoreNearbyTile, // 恢复附近分享磁贴
            DisableBluetooth, // 禁用蓝牙临时关闭
            LockScreenClockDisplaySeconds, // 时钟显示秒数
            BlurLockScreenButton, // 模糊锁屏界面按钮
            RemoveTheLeftSideOfTheLockScreen, // 移除锁屏负一屏功能
            RemoveLockScreenCamera, // 移除锁屏相机功能
            WaveCharge, // 启用Alpha充电动画
            LockScreenCurrent, // 充电时显示当前电流
            LockScreenDoubleTapToSleep, // 锁屏-双击锁定屏幕
            NoPasswordHook, // 开机免输密码
            OldQSCustom, // 自定义行列数
            // 状态栏网络速度 Start
            StatusBarNetworkSpeedRefreshSpeed,
            HideStatusBarNetworkSpeedSecond,
            HideNetworkSpeedSplitter,
            DoubleLineNetworkSpeed,
            // 状态栏网络速度 End
            // 隐藏图标 Start
            HideStatusBarIcon,
            HideBatteryIcon,
            HideHDIcon,
            HideSimIcon,
            HideMobileActivityIcon,
            HideMobileTypeIcon,
            HideWifiActivityIcon,
            // 隐藏图标 End
        )
    }
}