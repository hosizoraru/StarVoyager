package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.settings.EnableSpeedMode
import star.sky.voyager.hook.hooks.settings.NewNfcPage
import star.sky.voyager.hook.hooks.settings.NoThroughTheList
import star.sky.voyager.hook.hooks.settings.QuickInstallPermission
import star.sky.voyager.hook.hooks.settings.ShowNotificationImportance
import star.sky.voyager.utils.init.AppRegister

object Settings : AppRegister() {
    override val packageName: String = "com.android.settings"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            ShowNotificationImportance, // 显示通知重要程度
            EnableSpeedMode, // 显示极致模式选项
            QuickInstallPermission, // 安装未知应用权限免翻应用列表
            NoThroughTheList, // 显示在上层授权免翻应用列表
            NewNfcPage, // 新版 NFC 页面
//            MultipleFreeform,
        )
    }
}