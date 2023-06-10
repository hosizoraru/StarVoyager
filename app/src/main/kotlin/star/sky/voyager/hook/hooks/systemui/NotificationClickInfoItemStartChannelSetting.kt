package star.sky.voyager.hook.hooks.systemui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.service.notification.StatusBarNotification
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullAs
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object NotificationClickInfoItemStartChannelSetting : HookRegister() {
    override fun init() = hasEnable("notification_channel_setting") {
        var context: Context? = null
        var sbn: StatusBarNotification? = null
        loadClass("com.android.systemui.statusbar.notification.row.MiuiNotificationMenuRow")
            .methodFinder().filterByAssignableParamTypes(
                Context::class.java,
                loadClass("com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin\$MenuItem")
            ).filterByName("onClickInfoItem").first().createHook {
                before { param ->
                    sbn = getObjectOrNullAs<StatusBarNotification>(param.thisObject, "mSbn")
                    context = getObjectOrNullAs<Context>(param.thisObject, "mContext")
                }
                after {
                    sbn = null
                    context = null
                }
            }
        loadClass("com.android.systemui.statusbar.notification.NotificationSettingsHelper")
            .methodFinder().filterByAssignableParamTypes(
                Context::class.java,
                String::class.java,
                String::class.java,
                Int::class.javaPrimitiveType,
                String::class.java
            ).filterByName("startAppNotificationSettings").first().createHook {
                replace { param ->
                    val uid = param.args[3] as Int
                    startAppNotificationChannelSetting(context!!, sbn!!, uid)
                    return@replace null
                }
            }
    }

    private fun startAppNotificationChannelSetting(
        context: Context, sbn: StatusBarNotification, uid: Int
    ) {
        val intent: Intent = Intent(Intent.ACTION_MAIN)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .setClassName(
                "com.android.settings",
                "com.android.settings.SubSettings"
            )
            .putExtra(
                ":android:show_fragment",
                "com.android.settings.notification.ChannelNotificationSettings"
            )
            .putExtra(Settings.EXTRA_APP_PACKAGE, sbn.packageName)
            .putExtra(Settings.EXTRA_CHANNEL_ID, sbn.notification.channelId)
            .putExtra("app_uid", uid)

        intent.putExtra(Settings.EXTRA_CONVERSATION_ID, sbn.notification.shortcutId)

        val bundle = Bundle()
        bundle.putString(Settings.EXTRA_CHANNEL_ID, sbn.notification.channelId)

        bundle.putString(Settings.EXTRA_CONVERSATION_ID, sbn.notification.shortcutId)

        intent.putExtra(":android:show_fragment_args", bundle)

        val startActivityAsUser = context::class.java.getMethod(
            "startActivityAsUser",
            Intent::class.java,
            Class.forName("android.os.UserHandle")
        )

        val currentUserHandle = Class.forName("android.os.UserHandle")
            .getField("CURRENT")
            .get(null)

        startActivityAsUser.invoke(context, intent, currentUserHandle)
    }
}