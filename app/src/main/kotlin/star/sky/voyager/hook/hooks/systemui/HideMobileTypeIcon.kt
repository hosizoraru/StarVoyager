package star.sky.voyager.hook.hooks.systemui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XC_MethodHook
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getBoolean
import star.sky.voyager.utils.key.hasEnable

object HideMobileTypeIcon : HookRegister() {

    private val isBigType by lazy {
        getBoolean("big_mobile_type_icon", false)
    }

    override fun init() = hasEnable("hide_mobile_type_icon") {
        loadClass("com.android.systemui.statusbar.StatusBarMobileView").methodFinder()
            .filterByParamCount(1)
            .filter {
                name in setOf("initViewState", "updateState")
            }.toList().createHooks {
                after {
                    hideMobileTypeIcon(it)
                }
            }
    }

    private fun hideMobileTypeIcon(it: XC_MethodHook.MethodHookParam) {
        if (isBigType) {
            (it.thisObject.getObjectFieldAs<ImageView>("mMobileType")).visibility =
                View.GONE
            (it.thisObject.getObjectFieldAs<ImageView>("mMobileTypeImage")).visibility =
                View.GONE
            (it.thisObject.getObjectFieldAs<TextView>("mMobileTypeSingle")).visibility =
                View.GONE
        } else {
            (it.thisObject.getObjectFieldAs<ImageView>("mMobileType")).visibility =
                View.INVISIBLE
            (it.thisObject.getObjectFieldAs<ImageView>("mMobileTypeImage")).visibility =
                View.INVISIBLE
            (it.thisObject.getObjectFieldAs<TextView>("mMobileTypeSingle")).visibility =
                View.INVISIBLE
        }
    }
}