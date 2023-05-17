package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.securitycenter.LockOneHundred
import star.sky.voyager.hook.hooks.securitycenter.OpenByDefaultSetting
import star.sky.voyager.hook.hooks.securitycenter.RemoveConversationBubbleSettingsRestriction
import star.sky.voyager.hook.hooks.securitycenter.RemoveOpenAppConfirmationPopup
import star.sky.voyager.hook.hooks.securitycenter.ShowBatteryTemperature
import star.sky.voyager.hook.hooks.securitycenter.SkipWaitingTime
import star.sky.voyager.utils.init.AppRegister

object SecurityCenter : AppRegister() {
    override val packageName: String = "com.miui.securitycenter"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            ShowBatteryTemperature,
            RemoveConversationBubbleSettingsRestriction,
            SkipWaitingTime,
            RemoveOpenAppConfirmationPopup,
            LockOneHundred,
            OpenByDefaultSetting,
        )
    }
}