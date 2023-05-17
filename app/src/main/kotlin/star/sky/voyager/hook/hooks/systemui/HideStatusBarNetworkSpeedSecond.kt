package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object HideStatusBarNetworkSpeedSecond : HookRegister() {
    override fun init() = hasEnable("hide_status_bar_network_speed_second") {
        loadClass("com.android.systemui.statusbar.views.NetworkSpeedView").methodFinder().first {
            name == "setNetworkSpeed" && parameterCount == 1
        }.createHook {
            before {
                if (it.args[0] != null) {
                    val mText = (it.args[0] as String)
                        .replace("/", "")
                        .replace("s", "")
                        .replace("\'", "")
                        .replace("วิ", "")
                    it.args[0] = mText
                }
            }
        }
    }
}