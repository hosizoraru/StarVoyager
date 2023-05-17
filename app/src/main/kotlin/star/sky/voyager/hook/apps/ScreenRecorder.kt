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
            EnablePlaybackCapture, // 强制启用原生录音支持
            ModifyScreenRecorderConfig, // 修改码率与帧率范围
        )
    }
}