package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object CanNotificationSlide : HookRegister() {
    override fun init() = hasEnable("can_notification_slide") {
        loadClass("com.android.systemui.statusbar.notification.NotificationSettingsManager").methodFinder()
            .filterByName("canSlide")
            .first().createHook {
                after {
                    it.result = true
                }
            }
    }
}