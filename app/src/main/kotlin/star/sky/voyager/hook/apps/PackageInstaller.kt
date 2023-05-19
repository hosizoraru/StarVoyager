package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.packageinstaller.AllAsSystemApp
import star.sky.voyager.hook.hooks.packageinstaller.CustomInstallationSource
import star.sky.voyager.hook.hooks.packageinstaller.DisableCountCheck
import star.sky.voyager.hook.hooks.packageinstaller.DisableSafeModelTip
import star.sky.voyager.hook.hooks.packageinstaller.RemovePackageInstallerAds
import star.sky.voyager.hook.hooks.packageinstaller.ShowMoreApkInfo
import star.sky.voyager.utils.init.AppRegister

object PackageInstaller : AppRegister() {
    override val packageName: String = "com.miui.packageinstaller"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            DisableCountCheck, // 禁用频繁安装应用检查
            RemovePackageInstallerAds, // 去广告
            DisableSafeModelTip, // // 禁用安全守护提示
            AllAsSystemApp, // 将所有应用视为系统应用
            CustomInstallationSource, // 自定义安装来源
            ShowMoreApkInfo, // 显示更多应用信息
        )
    }
}