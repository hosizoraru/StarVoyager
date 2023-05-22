package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.music.RemoveOpenAd
import star.sky.voyager.utils.init.AppRegister

object Music : AppRegister() {
    override val packageName: String = "com.miui.player"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            RemoveOpenAd, // 去除开屏广告
        )
    }
}