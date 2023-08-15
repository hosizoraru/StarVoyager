package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.creation.CreationPhone
import star.sky.voyager.hook.hooks.creation.ImageSize
import star.sky.voyager.hook.hooks.multipackage.SuperClipboard
import star.sky.voyager.utils.init.AppRegister

object Creation : AppRegister() {
    override val packageName: String = "com.miui.creation"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            CreationPhone, // 为手机解锁小米创作
            ImageSize, // 解除图片比例限制
            SuperClipboard,
        )
    }
}