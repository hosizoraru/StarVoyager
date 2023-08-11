package star.sky.voyager.hook.hooks.settings

import android.app.Activity
import android.provider.Settings
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object QuickInstallPermission : HookRegister() {
    override fun init() = hasEnable("system_settings_permission_unknown_origin_app") {
        val settingsActivity =
            loadClass("com.android.settings.SettingsActivity")
        val redirectTabletActivity =
            settingsActivity.methodFinder().filterByName("redirectTabletActivity").first()

        redirectTabletActivity.createHook {
            before {
                val activity = it.thisObject as Activity
                val intent = activity.intent
                val action = intent.action
                val data = intent.data
                if (action != Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES || data == null || data.scheme != "package") return@before
                activity.objectHelper().setObjectUntilSuperclass(
                    "initialFragmentName",
                    "com.android.settings.applications.appinfo.ExternalSourcesDetails"
                )
            }
        }

        redirectTabletActivity.createHook {
            before {
                val activity = it.thisObject as Activity
                val intent = activity.intent
                val action = intent.action
                val data = intent.data
                if (action != Settings.ACTION_MANAGE_OVERLAY_PERMISSION || data == null || data.scheme != "package") return@before
                activity.objectHelper()
                    .setObjectUntilSuperclass(
                        "initialFragmentName",
                        "com.android.settings.applications.appinfo.DrawOverlayDetails"
                    )
            }
        }
    }
}