package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.callMethod
import star.sky.voyager.utils.api.setObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable

object MaximumNumberOfNotificationIcons : HookRegister() {
    override fun init() = hasEnable("max_notification_icon") {
        val icons = getInt("maximum_number_of_notification_icons", 3)
        val dots = getInt("maximum_number_of_notification_dots", 3)
        val icons2 = getInt("maximum_number_of_lockscreen_notification_icons", 3)
        loadClass("com.android.systemui.statusbar.phone.NotificationIconContainer").methodFinder()
            .filterByName("miuiShowNotificationIcons")
            .filterByParamCount(1)
            .first().createHook {
                replace {
                    if (it.args[0] as Boolean) {
                        it.thisObject.setObjectField("MAX_DOTS", dots)
                        it.thisObject.setObjectField("MAX_STATIC_ICONS", icons)
                        it.thisObject.setObjectField("MAX_ICONS_ON_LOCKSCREEN", icons2)
                    } else {
                        it.thisObject.setObjectField("MAX_DOTS", 0)
                        it.thisObject.setObjectField("MAX_STATIC_ICONS", 0)
                        it.thisObject.setObjectField("MAX_ICONS_ON_LOCKSCREEN", 0)
                    }
                    it.thisObject.callMethod("updateState")
                }
            }
    }
}