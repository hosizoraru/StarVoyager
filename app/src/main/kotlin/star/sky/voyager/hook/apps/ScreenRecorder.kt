package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.screenrecorder.EnablePlaybackCapture
import star.sky.voyager.hook.hooks.screenrecorder.ModifyScreenRecorderConfig
import star.sky.voyager.utils.init.AppRegister

object ScreenRecorder : AppRegister() {
    override val packageName: String = "com.miui.screenrecorder"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            EnablePlaybackCapture,
            ModifyScreenRecorderConfig,
        )
    }
}