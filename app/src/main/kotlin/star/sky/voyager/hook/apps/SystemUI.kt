package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.systemui.CanNotificationSlide
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
import star.sky.voyager.hook.hooks.systemui.MaximumNumberOfNotificationIcons
import star.sky.voyager.hook.hooks.systemui.NotificationSettingsNoWhiteList
import star.sky.voyager.hook.hooks.systemui.RestoreNearbyTile
import star.sky.voyager.hook.hooks.systemui.ShowWifiStandard
import star.sky.voyager.hook.hooks.systemui.StatusBarBattery
import star.sky.voyager.hook.hooks.systemui.StatusBarNetworkSpeedRefreshSpeed
import star.sky.voyager.hook.hooks.systemui.UseNewHD
import star.sky.voyager.hook.hooks.systemui.WaveCharge
import star.sky.voyager.utils.init.AppRegister

object SystemUI : AppRegister() {
    override val packageName: String = "com.android.systemui"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            RestoreNearbyTile,
            DisableBluetooth,
            CanNotificationSlide,
            StatusBarBattery,
            UseNewHD,
            WaveCharge,
            LockScreenCurrent,
            LockScreenClockDisplaySeconds,
            ShowWifiStandard,
            MaximumNumberOfNotificationIcons,
            NotificationSettingsNoWhiteList,
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