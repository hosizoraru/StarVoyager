package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.securitycenter.AppDisable
import star.sky.voyager.hook.hooks.securitycenter.BatteryTemperature
import star.sky.voyager.hook.hooks.securitycenter.EnhanceContours
import star.sky.voyager.hook.hooks.securitycenter.GetDefaultBubbles
import star.sky.voyager.hook.hooks.securitycenter.GunService
import star.sky.voyager.hook.hooks.securitycenter.LockOneHundred
import star.sky.voyager.hook.hooks.securitycenter.MEMC
import star.sky.voyager.hook.hooks.securitycenter.Macro
import star.sky.voyager.hook.hooks.securitycenter.OpenByDefaultSetting
import star.sky.voyager.hook.hooks.securitycenter.RemoveOpenAppConfirmationPopup
import star.sky.voyager.hook.hooks.securitycenter.Report
import star.sky.voyager.hook.hooks.securitycenter.RiskPkg
import star.sky.voyager.hook.hooks.securitycenter.ScreenHoldOn
import star.sky.voyager.hook.hooks.securitycenter.ScreenTime
import star.sky.voyager.hook.hooks.securitycenter.SkipWaitingTime
import star.sky.voyager.hook.hooks.securitycenter.SuperResolution
import star.sky.voyager.utils.init.AppRegister

object SecurityCenter : AppRegister() {
    override val packageName: String = "com.miui.securitycenter"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            SkipWaitingTime, // 跳过 5/10 秒警告时间
            RemoveOpenAppConfirmationPopup, // 移除打开应用弹窗
            LockOneHundred, // 手机管家锁定100分
            BatteryTemperature, // 电池页面显示当前温度
            OpenByDefaultSetting, // 将应用详情中“消除默认操作”改为“默认打开”设置
            Report, // 移除应用详情界面的举报
            RiskPkg, // 禁用发现恶意应用通知
            AppDisable, // 在应用详情界面显示停用App
            ScreenHoldOn, // 解锁熄屏挂机和息屏听剧
            MEMC, // 全局开放动态画面补偿
            EnhanceContours, // 全局开放影像轮廓增强
            SuperResolution, // 全局开放极清播放
            GunService, // 全局开放准心辅助
            Macro, // 全局开放自动连招
            ScreenTime, // 开放屏幕时间
            GetDefaultBubbles, // 去除气泡通知应用限制
        )
    }
}