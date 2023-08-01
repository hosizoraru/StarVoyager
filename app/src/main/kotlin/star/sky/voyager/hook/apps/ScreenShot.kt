package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.mediaeditor.UnlockUnlimitedCropping
import star.sky.voyager.hook.hooks.multipackage.SuperClipboard
import star.sky.voyager.hook.hooks.screenshot.DeviceShell
import star.sky.voyager.hook.hooks.screenshot.SaveAsPng
import star.sky.voyager.hook.hooks.screenshot.SaveToPictures
import star.sky.voyager.utils.init.AppRegister

object ScreenShot : AppRegister() {
    override val packageName: String = "com.miui.screenshot"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            UnlockUnlimitedCropping, // 移除裁剪图片/屏幕截图的限制
            SuperClipboard,
            SaveToPictures, // 截图保存到 Pictures/Screenshots
            SaveAsPng, // 截图格式为 PNG
            DeviceShell,
        )
    }
}