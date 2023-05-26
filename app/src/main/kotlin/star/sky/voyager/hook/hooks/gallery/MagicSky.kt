package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object MagicSky : HookRegister() {
    override fun init() = hasEnable("enable_magic_sky") {
        loadClass("com.miui.gallery.util.FilterSkyEntranceUtils").methodFinder().first {
            name == "showSingleFilterSky"
        }.createHook {
            before {
                it.result = true
            }
        }
    }
}