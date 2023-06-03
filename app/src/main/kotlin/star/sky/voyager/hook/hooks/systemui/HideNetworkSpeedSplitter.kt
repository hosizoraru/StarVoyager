package star.sky.voyager.hook.hooks.systemui

import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object HideNetworkSpeedSplitter : HookRegister() {
    override fun init() = hasEnable("hide_network_speed_splitter") {
        loadClass("com.android.systemui.statusbar.views.NetworkSpeedSplitter").methodFinder()
            .filterByName("init")
            .first().createHook {
                after {
                    val textView = it.thisObject as TextView
                    textView.text = " "
                }
            }
    }
}