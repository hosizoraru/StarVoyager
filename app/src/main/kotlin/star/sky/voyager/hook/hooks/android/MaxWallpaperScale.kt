package star.sky.voyager.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.setObject
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getFloat

object MaxWallpaperScale : HookRegister() {
    override fun init() {
        loadClass("com.android.server.wm.WallpaperController").constructors.createHooks {
            after {
                val value = getFloat("max_wallpaper_scale", 1.2f)
                setObject(it.thisObject, "mMaxWallpaperScale", value)
            }
        }
    }
}