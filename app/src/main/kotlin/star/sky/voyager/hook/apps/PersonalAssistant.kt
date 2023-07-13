package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.personalassistant.BlurPersonalAssistant
import star.sky.voyager.hook.hooks.personalassistant.ExposureRefreshForNonMIUIWidget
import star.sky.voyager.hook.hooks.personalassistant.LocationCN
import star.sky.voyager.utils.init.AppRegister

object PersonalAssistant : AppRegister() {
    override val packageName: String = "com.miui.personalassistant"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            BlurPersonalAssistant, // 负一屏模糊
            ExposureRefreshForNonMIUIWidget, // 为非MIUI小部件提供曝光刷新
            LocationCN,
        )
    }
}