package star.sky.voyager.hook.hooks.misettings

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object AllFps : HookRegister() {
    override fun init() = hasEnable("show_all_fps") {
        loadClass("miui.util.FeatureParser")
            .methodFinder()
            .filterByName("getIntArray")
            .toList()
            .createHooks {
                before {
                    if (it.args[0] == "fpsList") {
                        it.result = intArrayOf(144, 120, 90, 60, 30)
                    }
                }
            }
    }
}