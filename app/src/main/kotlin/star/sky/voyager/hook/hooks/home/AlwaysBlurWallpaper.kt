package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable

object AlwaysBlurWallpaper : HookRegister() {
    private val value by lazy {
        getInt("home_blur_radius", 100)
    }

    override fun init() = hasEnable("home_blur_wallpaper") {
        loadClass("com.miui.home.launcher.common.BlurUtils").methodFinder()
            .filterByName("fastBlur")
            .filterByParamCount(4)
            .first().createHook {
                before {
                    it.args[0] = value.toFloat() / 100
                    it.args[2] = true
                }
            }
    }
}