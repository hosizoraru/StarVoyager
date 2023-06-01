package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.mediaeditor.UnlockUnlimitedCropping
import star.sky.voyager.hook.hooks.screenshot.SaveToPictures
import star.sky.voyager.utils.init.AppRegister

object ScreenShot : AppRegister() {
    override val packageName: String = "com.miui.screenshot"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            SaveToPictures, // 截图保存到 Pictures/Screenshots
            UnlockUnlimitedCropping // 移除裁剪图片/屏幕截图的限制
        )
    }
}