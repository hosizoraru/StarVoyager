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
        val SuperWallpaperUtilsClass = loadClass("com.miui.superwallpaper.SuperWallpaperUtils")
        SuperWallpaperUtilsClass.methodFinder().first {
            name == "initEnableSuperWallpaper" && parameterTypes[0] == Context::class.java
        }.createHook {
            before { param ->
                param.result = true
            }
        }
        setStaticObject(
            SuperWallpaperUtilsClass,
            "sEnableSuperWallpaper",
            true
        )
    }
}