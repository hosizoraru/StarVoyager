package star.sky.voyager.hook.hooks.systemui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XC_MethodHook
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object HideWifiActivityIcon : HookRegister() {
    override fun init() {
        loadClass("com.android.systemui.statusbar.StatusBarWifiView").methodFinder()
            .filterByName("initViewState")
            .filterByParamCount(1)
            .first().createHook {
                after {
                    hide(it)
                }
            }

        loadClass("com.android.systemui.statusbar.StatusBarWifiView").methodFinder()
            .filterByName("updateState")
            .filterByParamCount(1)
            .first().createHook {
                after {
                    hide(it)
                }
            }
    }

    private fun hide(it: XC_MethodHook.MethodHookParam) {
        //隐藏WIFI箭头
        hasEnable("hide_wifi_activity_icon") {
            (it.thisObject.getObjectFieldAs<ImageView>("mWifiActivityView")).visibility =
                View.INVISIBLE
        }
        //隐藏WIFI标准图标
        hasEnable("hide_wifi_standard_icon") {
            (it.thisObject.getObjectFieldAs<TextView>("mWifiStandardView")).visibility =
                View.INVISIBLE
        }
    }
}