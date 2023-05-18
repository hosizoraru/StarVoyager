package star.sky.voyager.hook.hooks.aod

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object UnlockAlwaysOnDisplay : HookRegister() {
    override fun init() = hasEnable("unlock_always_on_display_time") {
        loadClass("com.miui.aod.widget.AODSettings").methodFinder().first {
            name == "onlySupportKeycodeGoto"
        }.createHook {
            before { param ->
                param.result = false
            }
        }
    }
}