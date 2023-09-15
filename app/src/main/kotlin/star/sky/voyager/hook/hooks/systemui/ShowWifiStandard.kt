package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.api.setObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object ShowWifiStandard : HookRegister() {
    override fun init() = hasEnable("show_wifi_standard") {
        val statusBarWifiViewCls =
            loadClass("com.android.systemui.statusbar.StatusBarWifiView")
        val wifiIconStateCls =
            loadClass("com.android.systemui.statusbar.phone.StatusBarSignalPolicy\$WifiIconState")

        statusBarWifiViewCls.methodFinder().filter {
            parameterCount == 1 &&
                    name in setOf("initViewState", "updateState")
        }.toList().createHooks {
            before {
                copyTo(wifiIconStateCls)
            }
        }
    }

    private fun copyTo(wifiIconStateClass: Class<*>) {
        wifiIconStateClass.methodFinder()
            .filterByName("copyTo")
            .filterByParamCount(1)
            .first().createHook {
                before {
                    val wifiStandard = it.thisObject.getObjectFieldAs<Int>("wifiStandard")
                    it.thisObject.setObjectField("showWifiStandard", wifiStandard != 0)
                }
            }
    }
}