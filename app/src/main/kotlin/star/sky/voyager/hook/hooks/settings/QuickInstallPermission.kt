package star.sky.voyager.hook.hooks.settings

import android.app.Activity
import android.os.Bundle
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.setObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object QuickInstallPermission : HookRegister() {
    override fun init() = hasEnable("system_settings_permission_unknown_origin_app") {
        loadClass("com.android.settings.SettingsActivity").methodFinder()
            .filterByName("redirectTabletActivity")
            .filterByParamTypes(Bundle::class.java)
            .toList().createHooks {
                before { param ->
                    val intent = (param.thisObject as Activity).intent
                    if ("android.settings.MANAGE_UNKNOWN_APP_SOURCES" == intent.action && intent.data != null && "package" == intent.data!!.scheme) {
                        param.thisObject.setObjectField(
                            "initialFragmentName",
                            "com.android.settings.applications.appinfo.ExternalSourcesDetails"
                        )
                    }
                }
            }
    }
}