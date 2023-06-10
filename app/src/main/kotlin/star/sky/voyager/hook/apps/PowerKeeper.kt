package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.multipackage.CustomRefreshRate
import star.sky.voyager.hook.hooks.powerkeeper.DoNotClearApp
import star.sky.voyager.hook.hooks.powerkeeper.DoNotClearAppPlusP
import star.sky.voyager.hook.hooks.powerkeeper.PreventRecoveryOfBatteryOptimizationWhitelist
import star.sky.voyager.utils.init.AppRegister

object PowerKeeper : AppRegister() {
    override val packageName: String = "com.miui.powerkeeper"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            CustomRefreshRate, // 自定义高刷新率应用
            DoNotClearApp, // 防止杀死后台应用
            DoNotClearAppPlusP, // 更激进的防止杀死后台应用
            PreventRecoveryOfBatteryOptimizationWhitelist, // 防止恢复电池优化白名单
        )
    }
}