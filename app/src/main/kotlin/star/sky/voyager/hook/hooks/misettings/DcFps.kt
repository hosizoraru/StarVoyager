package star.sky.voyager.hook.hooks.misettings

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object DcFps : HookRegister() {
    override fun init() = hasEnable("dc_fps") {
        loadClass("miui.util.FeatureParser").methodFinder()
            .filterByName("getBoolean")
            .toList()
            .createHooks {
                before {
                    if (it.args[0] == "dc_backlight_fps_incompatible") {
                        it.result = false
                    }
                }
            }
    }
}