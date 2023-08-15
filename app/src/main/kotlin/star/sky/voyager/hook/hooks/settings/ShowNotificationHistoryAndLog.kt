package star.sky.voyager.hook.hooks.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.AttributeSet
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object ShowNotificationHistoryAndLog : HookRegister() {
    override fun init() = hasEnable("show_notification_history_and_log") {
        loadClass("com.android.settings.NotificationControlCenterSettings").methodFinder()
            .filterByName("onCreate").first()
            .createHook {
                after {
                    val thisObject = it.thisObject
                    val resources = invokeMethodBestMatch(thisObject, "getResources") as Resources
                    val notificationSettings = invokeMethodBestMatch(
                        thisObject,
                        "findPreference",
                        null,
                        "notification_settings"
                    )!!
                    val preferenceManager =
                        invokeMethodBestMatch(thisObject, "getPreferenceManager")!!
                    val context = invokeMethodBestMatch(preferenceManager, "getContext") as Context
                    val preferenceScreenForNotificationHistory =
                        generatePreferenceScreenForNotificationHistory(context, resources)
                    val preferenceScreenForNotificationLog =
                        generatePreferenceScreenForNotificationLog(context, resources)
                    invokeMethodBestMatch(
                        notificationSettings,
                        "addPreference",
                        null,
                        preferenceScreenForNotificationHistory
                    )
                    invokeMethodBestMatch(
                        notificationSettings,
                        "addPreference",
                        null,
                        preferenceScreenForNotificationLog
                    )
                }
            }
    }

    @SuppressLint("DiscouragedApi")
    private fun generatePreferenceScreenForNotificationHistory(
        context: Context,
        resources: Resources
    ): Any {
        val preferenceScreenForNotificationHistory =
            loadClass("androidx.preference.PreferenceScreen")
                .getDeclaredConstructor(
                    Context::class.java, AttributeSet::class.java
                ).newInstance(context, null)
        invokeMethodBestMatch(
            preferenceScreenForNotificationHistory,
            "setIntent",
            null,
            Intent().apply {
                action = Intent.ACTION_MAIN
                setClassName(
                    "com.android.settings",
                    "com.android.settings.notification.history.NotificationHistoryActivity"
                )
            })
        invokeMethodBestMatch(
            preferenceScreenForNotificationHistory,
            "setTitle",
            null,
            resources.getIdentifier(
                "notification_history_title",
                "string",
                hostPackageName
            )
        )
        return preferenceScreenForNotificationHistory
    }

    @SuppressLint("DiscouragedApi")
    private fun generatePreferenceScreenForNotificationLog(
        context: Context,
        resources: Resources
    ): Any {
        val preferenceScreenForNotificationLog =
            loadClass("androidx.preference.PreferenceScreen")
                .getDeclaredConstructor(
                    Context::class.java, AttributeSet::class.java
                ).newInstance(context, null)
        invokeMethodBestMatch(
            preferenceScreenForNotificationLog,
            "setIntent",
            null,
            Intent().apply {
                action = Intent.ACTION_MAIN
                setClassName(
                    "com.android.settings",
                    "com.android.settings.Settings\$NotificationStationActivity"
                )
            })
        invokeMethodBestMatch(
            preferenceScreenForNotificationLog,
            "setTitle",
            null,
            resources.getIdentifier("notification_log_title", "string", hostPackageName)
        )
        return preferenceScreenForNotificationLog
    }
}