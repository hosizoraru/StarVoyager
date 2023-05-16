package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.api.setObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object ShowWifiStandard : HookRegister() {
    override fun init() = hasEnable("show_wifi_standard") {
        loadClass("com.android.systemui.statusbar.StatusBarWifiView").methodFinder().first {
            name == "initViewState" && parameterCount == 1
        }.createHook {
            before {
                loadClass("com.android.systemui.statusbar.phone.StatusBarSignalPolicy\$WifiIconState").methodFinder()
                    .first {
                        name == "copyTo" && parameterCount == 1
                    }.createHook {
                    before {
                        val wifiStandard = it.thisObject.getObjectFieldAs<Int>("wifiStandard")
                        it.thisObject.setObjectField("showWifiStandard", wifiStandard != 0)
                    }
                }
            }
        }

        loadClass("com.android.systemui.statusbar.StatusBarWifiView").methodFinder().first {
            name == "updateState" && parameterCount == 1
        }.createHook {
            before {
                loadClass("com.android.systemui.statusbar.phone.StatusBarSignalPolicy\$WifiIconState").methodFinder()
                    .first {
                        name == "copyTo" && parameterCount == 1
                    }.createHook {
                    before {
                        val wifiStandard = it.thisObject.getObjectFieldAs<Int>("wifiStandard")
                        it.thisObject.setObjectField("showWifiStandard", wifiStandard != 0)
                    }
                }
            }
        }
    }
}