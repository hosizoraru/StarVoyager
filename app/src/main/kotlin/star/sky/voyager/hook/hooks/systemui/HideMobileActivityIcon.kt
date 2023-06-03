package star.sky.voyager.hook.hooks.systemui

import android.view.View
import android.widget.ImageView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XC_MethodHook
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object HideMobileActivityIcon : HookRegister() {
    override fun init() = hasEnable("hide_mobile_activity_icon") {
        loadClass("com.android.systemui.statusbar.StatusBarMobileView").methodFinder()
            .filterByName("initViewState")
            .filterByParamCount(1)
            .first().createHook {
                after {
                    hide(it)
                }
            }

        loadClass("com.android.systemui.statusbar.StatusBarMobileView").methodFinder()
            .filterByName("updateState")
            .filterByParamCount(1)
            .first().createHook {
                after {
                    hide(it)
                }
            }
    }

    private fun hide(it: XC_MethodHook.MethodHookParam) {
        (it.thisObject.getObjectFieldAs<ImageView>("mLeftInOut")).visibility = View.GONE
        (it.thisObject.getObjectFieldAs<ImageView>("mRightInOut")).visibility = View.GONE
    }
}