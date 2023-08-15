package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.aiasstvision.DesktopModeScreenTranslate
import star.sky.voyager.hook.hooks.aiasstvision.MeetingMode
import star.sky.voyager.hook.hooks.aiasstvision.SplitScreenTranslate
import star.sky.voyager.utils.init.AppRegister

object AiasstVision : AppRegister() {
    override val packageName: String = "com.xiaomi.aiasst.vision"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            MeetingMode, // 解锁会议模式
            DesktopModeScreenTranslate, // 允许在工作台模式下使用屏幕翻译
            SplitScreenTranslate, // 解锁分屏翻译
        )
    }
}