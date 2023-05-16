package star.sky.voyager.hook.hooks.settings

import android.app.NotificationChannel
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XposedHelpers
import star.sky.voyager.utils.api.callMethod
import star.sky.voyager.utils.api.callMethodAs
import star.sky.voyager.utils.api.getObjectField
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object ShowNotificationImportance : HookRegister() {
    override fun init() = hasEnable("show_notification_importance") {
        loadClass("com.android.settings.notification.ChannelNotificationSettings").methodFinder()
            .first {
                name == "removeDefaultPrefs"
            }.createHook {
                before {
                    val importance =
                        it.thisObject.callMethod("findPreference", "importance")
                            ?: return@before
                    val mChannel = it.thisObject.getObjectField("mChannel") as NotificationChannel
                    val index = importance.callMethodAs<Int>(
                        "findSpinnerIndexOfValue",
                        mChannel.importance.toString()
                    )
                    if (index < 0) return@before
                    importance.callMethod("setValueIndex", index)
                    XposedHelpers.setAdditionalInstanceField(
                        importance,
                        "channelNotificationSettings",
                        it.thisObject
                    )
                    it.result = null
                }
            }

        loadClass("androidx.preference.Preference").methodFinder().first {
            name == "callChangeListener" && parameterTypes[0] == Any::class.java
        }.createHook {
            after {
                val channelNotificationSettings = XposedHelpers.getAdditionalInstanceField(
                    it.thisObject,
                    "channelNotificationSettings"
                ) ?: return@after
                val mChannel =
                    channelNotificationSettings.getObjectField("mChannel") as NotificationChannel
                mChannel.callMethod(
                    "setImportance",
                    null,
                    (it.args[0] as String).toInt()
                )
                val mBackend =
                    channelNotificationSettings.getObjectField("mBackend") ?: return@after
                val mPkg = channelNotificationSettings.getObjectFieldAs<String>("mPkg")
                val mUid = channelNotificationSettings.getObjectFieldAs<Int>("mUid")
                mBackend.callMethod("updateChannel", mPkg, mUid, mChannel)
            }
        }
    }
}