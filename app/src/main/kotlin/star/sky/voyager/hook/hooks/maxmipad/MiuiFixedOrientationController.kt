package star.sky.voyager.hook.hooks.maxmipad

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.XSharedPreferences

object MiuiFixedOrientationController : HookRegister() {
    override fun init() = hasEnable("disable_fixed_orientation") {
        val shouldDisableFixedOrientationList =
            XSharedPreferences.getStringSet("should_disable_fixed_orientation_list", mutableSetOf())
        loadClass("com.android.server.wm.MiuiFixedOrientationController").methodFinder().filter {
            name == "shouldDisableFixedOrientation"
        }.toList().createHooks {
            before { param ->
                if (param.args[0] in shouldDisableFixedOrientationList) {
                    param.result = true
                }
            }
        }
    }
}