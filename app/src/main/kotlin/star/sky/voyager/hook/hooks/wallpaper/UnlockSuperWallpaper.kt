package star.sky.voyager.hook.hooks.wallpaper

import android.content.Context
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object UnlockSuperWallpaper : HookRegister() {
    override fun init() = hasEnable("unlock_super_wallpaper") {
        val superWallpaperUtilsClass = loadClass("com.miui.superwallpaper.SuperWallpaperUtils")
        superWallpaperUtilsClass.methodFinder()
            .filterByName("initEnableSuperWallpaper")
            .filterByParamTypes(Context::class.java)
            .first().createHook {
                before { param ->
                    param.result = true
                }
            }
        setStaticObject(
            superWallpaperUtilsClass,
            "sEnableSuperWallpaper",
            true
        )
    }
}