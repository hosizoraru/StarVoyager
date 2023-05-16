package star.sky.voyager.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import star.sky.voyager.utils.api.hookAfterAllConstructors
import star.sky.voyager.utils.api.setObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getFloat

object MaxWallpaperScale : HookRegister() {
    override fun init() {
        loadClass("com.android.server.wm.WallpaperController").hookAfterAllConstructors {
            val value = getFloat("max_wallpaper_scale", 1.2f)
            it.thisObject.setObjectField("mMaxWallpaperScale", value)
        }
    }
}