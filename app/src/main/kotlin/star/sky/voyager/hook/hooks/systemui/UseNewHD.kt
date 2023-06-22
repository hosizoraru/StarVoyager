package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getBoolean
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.WifiState.isWifiEnabled

object UseNewHD : HookRegister() {
    override fun init() = hasEnable("system_ui_use_new_hd") {
        val noShowOnWifi = getBoolean("no_show_on_wifi", false)
        runCatching {
            loadClass("com.android.systemui.statusbar.policy.HDController").methodFinder()
                .filterByName("isVisible")
                .first().createHook {
                    when (noShowOnWifi) {
                        true -> {
                            replace {
                                !isWifiEnabled()
                            }
                        }

                        false -> {
                            returnConstant(true)
                        }
                    }
                }
        }
    }
}