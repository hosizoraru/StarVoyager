package star.sky.voyager.hook.hooks.settings

import android.app.NotificationChannel
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullUntilSuperclass
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullUntilSuperclassAs
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XposedHelpers.getAdditionalInstanceField
import de.robv.android.xposed.XposedHelpers.setAdditionalInstanceField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object ShowNotificationImportance : HookRegister() {
    override fun init() = hasEnable("show_notification_importance") {
        loadClass("com.android.settings.notification.ChannelNotificationSettings").methodFinder()
            .filterByName("removeDefaultPrefs").first().createHook {
                before {
                    val importance =
                        invokeMethodBestMatch(it.thisObject, "findPreference", null, "importance")
                            ?: return@before
                    val mChannel = getObjectOrNullUntilSuperclassAs<NotificationChannel>(
                        it.thisObject,
                        "mChannel"
                    )!!
                    val index = invokeMethodBestMatch(
                        importance, "findSpinnerIndexOfValue", null, mChannel.importance.toString()
                    )!! as Int
                    if (index < 0) return@before
                    invokeMethodBestMatch(importance, "setValueIndex", null, index)
                    setAdditionalInstanceField(
                        importance,
                        "channelNotificationSettings",
                        it.thisObject
                    )
                    it.result = null
                }
            }
        loadClass("androidx.preference.Preference").methodFinder()
            .filterByName("callChangeListener")
            .filterByParamTypes(Any::class.java).first().createHook {
                after {
                    val channelNotificationSettings =
                        getAdditionalInstanceField(it.thisObject, "channelNotificationSettings")
                            ?: return@after
                    val mChannel =
                        getObjectOrNullUntilSuperclassAs<NotificationChannel>(
                            channelNotificationSettings,
                            "mChannel"
                        )!!
                    invokeMethodBestMatch(
                        mChannel,
                        "setImportance",
                        null,
                        (it.args[0] as String).toInt()
                    )
                    val mBackend =
                        getObjectOrNullUntilSuperclass(channelNotificationSettings, "mBackend")!!
                    val mPkg = getObjectOrNullUntilSuperclassAs<String>(
                        channelNotificationSettings,
                        "mPkg"
                    )
                    val mUid =
                        getObjectOrNullUntilSuperclassAs<Int>(channelNotificationSettings, "mUid")
                    invokeMethodBestMatch(mBackend, "updateChannel", null, mPkg, mUid, mChannel)
                }
            }
    }
}