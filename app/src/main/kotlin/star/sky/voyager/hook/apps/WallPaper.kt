package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.wallpaper.UnlockSuperWallpaper
import star.sky.voyager.utils.init.AppRegister

object WallPaper : AppRegister() {
    override val packageName: String = "com.miui.miwallpaper"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            UnlockSuperWallpaper, // 解锁超级壁纸
        )
    }
}