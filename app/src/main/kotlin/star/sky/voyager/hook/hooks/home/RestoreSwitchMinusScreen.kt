package star.sky.voyager.hook.hooks.home

import android.content.Intent
import android.os.Bundle
import com.github.kyuubiran.ezxhelper.ClassUtils.invokeStaticMethodBestMatch
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNull
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.MiuiBuildCls

object RestoreSwitchMinusScreen : HookRegister() {
    override fun init() = hasEnable("restore_switch_minus_screen") {
        val clazzUtilities = loadClass("com.miui.home.launcher.common.Utilities")
        val clazzLauncher = loadClass("com.miui.home.launcher.Launcher")
        val clazzMiuiHomeSettings = loadClass("com.miui.home.settings.MiuiHomeSettings")
        loadClass("com.miui.home.launcher.DeviceConfig").methodFinder()
            .filterByName("isUseGoogleMinusScreen").first()
            .createHook {
                before {
                    setStaticObject(
                        loadClass("com.miui.home.launcher.LauncherAssistantCompat"),
                        "CAN_SWITCH_MINUS_SCREEN",
                        true
                    )
                }
            }
        loadClass("com.miui.home.launcher.LauncherAssistantCompat").methodFinder()
            .filterByName("newInstance")
            .filterByAssignableParamTypes(clazzLauncher).first().createHook {
                before {
                    val isPersonalAssistantGoogle = (invokeStaticMethodBestMatch(
                        clazzUtilities, "getCurrentPersonalAssistant"
                    )!! as String) == "personal_assistant_google"
                    setStaticObject(
                        MiuiBuildCls,
                        "IS_INTERNATIONAL_BUILD",
                        isPersonalAssistantGoogle
                    )
                }
                after {
                    setStaticObject(MiuiBuildCls, "IS_INTERNATIONAL_BUILD", false)
                }
            }
        clazzLauncher.declaredConstructors.createHooks {
            before {
                setStaticObject(MiuiBuildCls, "IS_INTERNATIONAL_BUILD", true)
            }
            after {
                setStaticObject(MiuiBuildCls, "IS_INTERNATIONAL_BUILD", false)
            }
        }
        clazzMiuiHomeSettings.methodFinder().filterByName("onCreatePreferences")
            .filterByAssignableParamTypes(Bundle::class.java, String::class.java).first()
            .createHook {
                after { param ->
                    val mSwitchPersonalAssistant =
                        getObjectOrNull(param.thisObject, "mSwitchPersonalAssistant")!!
                    mSwitchPersonalAssistant.objectHelper {
                        invokeMethodBestMatch(
                            "setIntent",
                            null,
                            Intent("com.miui.home.action.LAUNCHER_PERSONAL_ASSISTANT_SETTING")
                        )
                        invokeMethodBestMatch(
                            "setOnPreferenceChangeListener",
                            null,
                            param.thisObject
                        )
                    }
                    invokeMethodBestMatch(param.thisObject, "getPreferenceScreen")!!.objectHelper()
                        .invokeMethodBestMatch("addPreference", null, mSwitchPersonalAssistant)
                }
            }
        clazzMiuiHomeSettings.methodFinder().filterByName("onResume").first().createHook {
            after { param ->
                getObjectOrNull(param.thisObject, "mSwitchPersonalAssistant")!!.objectHelper()
                    .invokeMethodBestMatch("setVisible", null, true)
            }
        }
    }
}