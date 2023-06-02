package star.sky.voyager.hook.hooks.systemui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.service.notification.StatusBarNotification
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullAs
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import dev.rikka.tools.refine.Refine.unsafeCast
import star.sky.voyager.utils.api.ContextHidden
import star.sky.voyager.utils.api.UserHandleHidden
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object NotificationClickInfoItemStartChannelSetting : HookRegister() {
    override fun init() = hasEnable("") {
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
                    Log.i("context: $context")
                    Log.i("sbn: $sbn")
                    Log.i("uid: $uid")
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
                // miui 自己修改了标准的 .notification.app 下的 ChannelNotificationSettings 代码后，
                // 拷贝了一份到 .notification 又稍作修改。在系统设置中实际使用了 .notification 的。
                // 并且"显示通知渠道设置"的 Xposed 模块作用的仅是 .notification 的。
                ":android:show_fragment",
                "com.android.settings.notification.ChannelNotificationSettings"
            )
            .putExtra(Settings.EXTRA_APP_PACKAGE, sbn.packageName)
            .putExtra(Settings.EXTRA_CHANNEL_ID, sbn.notification.channelId)
            .putExtra("app_uid", uid) // miui non-standard dual apps shit
        intent.putExtra(Settings.EXTRA_CONVERSATION_ID, sbn.notification.shortcutId)
        // workaround for miui 12.5, 金凡！！！
        val bundle = Bundle()
        bundle.putString(Settings.EXTRA_CHANNEL_ID, sbn.notification.channelId)
        bundle.putString(Settings.EXTRA_CONVERSATION_ID, sbn.notification.shortcutId)
        intent.putExtra(":android:show_fragment_args", bundle)
        Log.i("context: $context")
        Log.i("context class: ${context.javaClass.name}")
        Log.i("context package: ${context.packageName}")
        Log.i("intent: $intent")
        unsafeCast<ContextHidden>(context)
            .startActivityAsUser(intent, UserHandleHidden.CURRENT)
    }
}