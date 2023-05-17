package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassHelper.Companion.classHelper
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import de.robv.android.xposed.XposedHelpers
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object NotificationSettingsNoWhiteList : HookRegister() {
    override fun init() = hasEnable("notification_settings_no_white_list") {
        if (loadClass("miui.os.Build").classHelper()
                .getStaticObjectOrNullAs<Boolean>("IS_INTERNATIONAL_BUILD")!!
        ) return@hasEnable
        XposedHelpers.setStaticObjectField(
            loadClass("com.android.systemui.statusbar.notification.NotificationSettingsManager"),
            "USE_WHITE_LISTS",
            false
        )
    }
}