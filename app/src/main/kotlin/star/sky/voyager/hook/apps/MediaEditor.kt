package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.mediaeditor.DeviceShell2
import star.sky.voyager.hook.hooks.mediaeditor.FilterManager
import star.sky.voyager.hook.hooks.mediaeditor.MarketName
import star.sky.voyager.hook.hooks.mediaeditor.UnlockUnlimitedCropping
import star.sky.voyager.utils.init.AppRegister

object MediaEditor : AppRegister() {
    override val packageName: String = "com.miui.mediaeditor"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            FilterManager, // 大师滤镜
            UnlockUnlimitedCropping, // 移除裁剪图片/屏幕截图的限制
            DeviceShell2,
            MarketName,
        )
    }
}