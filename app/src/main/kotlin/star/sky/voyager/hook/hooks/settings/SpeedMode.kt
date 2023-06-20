package star.sky.voyager.hook.hooks.settings

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object SpeedMode : HookRegister() {
    override fun init() = hasEnable("speed_mode") {
        loadClass("com.android.settings.development.SpeedModeToolsPreferenceController").methodFinder()
            .filterByName("getAvailabilityStatus")
            .first().createHook {
                after {
                    it.result = 0
                }
            }
    }
}