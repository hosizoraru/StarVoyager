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
        val StatusBarWifiViewClass = loadClass("com.android.systemui.statusbar.StatusBarWifiView")
        val WifiIconStateClass =
            loadClass("com.android.systemui.statusbar.phone.StatusBarSignalPolicy\$WifiIconState")
        StatusBarWifiViewClass.methodFinder().first {
            name == "initViewState" && parameterCount == 1
        }.createHook {
            before {
                WifiIconStateClass.methodFinder()
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

        StatusBarWifiViewClass.methodFinder().first {
            name == "updateState" && parameterCount == 1
        }.createHook {
            before {
                WifiIconStateClass.methodFinder()
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