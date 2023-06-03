package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object StatusBarNetworkSpeedRefreshSpeed : HookRegister() {
    override fun init() = hasEnable("status_bar_network_speed_refresh_speed") {
        loadClass("com.android.systemui.statusbar.policy.NetworkSpeedController").methodFinder()
            .filterByName("postUpdateNetworkSpeedDelay")
            .filterByParamTypes(Long::class.java)
            .first().createHook {
                before {
                    it.args[0] = 1000L
                }
            }
    }
}