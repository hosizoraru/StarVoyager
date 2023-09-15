package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.setObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object DisableRecentViewWallpaperDarkening : HookRegister() {
    override fun init() = hasEnable("home_recent_view_wallpaper_darkening") {
        loadClass("com.miui.home.recents.DimLayer").methodFinder()
            .filterByName("dim")
            .filterByParamCount(3)
            .first().createHook {
                before {
                    it.args[0] = 0.0f
                    it.thisObject.setObjectField("mCurrentAlpha", 0.0f)
//                    it.thisObject.objectHelper().setObject("mCurrentAlpha", 0.0f)
                }
            }
    }
}