package star.sky.voyager.hook.hooks.newmaxmipad

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.XSharedPreferences.getStringSet

object DisableFixedOrientation : HookRegister() {
    override fun init() = hasEnable("disable_fixed_orientation") {
        val shouldDisableFixedOrientationList =
            getStringSet("should_disable_fixed_orientation_list", mutableSetOf())
        loadClass("com.android.server.wm.MiuiFixedOrientationController")
            .methodFinder().filterByName("shouldDisableFixedOrientation")
            .first().createHook {
                before {
                    if (it.args[0] in shouldDisableFixedOrientationList) {
                        it.result = true
                    }
                }
            }
    }
}