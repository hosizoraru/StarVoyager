package star.sky.voyager.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.setObject
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getFloat

object MaxWallpaperScale : HookRegister() {
    private val value by lazy {
        getFloat("max_wallpaper_scale", 1.2f)
    }

    override fun init() {
        if (value == 1.2f) return

        loadClass("com.android.server.wm.WallpaperController")
            .declaredConstructors.createHooks {
                after {
                    setObject(it.thisObject, "mMaxWallpaperScale", value)
                }
            }
    }
}