package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object PadHideApp : HookRegister() {
    override fun init() = hasEnable("pad_hide_app") {
        val miuiHomeSettings =
            loadClass("com.miui.home.settings.MiuiHomeSettings")
        val deviceConfig =
            loadClass("com.miui.home.launcher.DeviceConfig")

        miuiHomeSettings.methodFinder().filter {
            name in setOf("needHideMinusScreen", "shouldHidePersonalAssistantSettings")
        }.toList().createHooks {
            returnConstant(false)
        }

        miuiHomeSettings.methodFinder().filter {
            name in setOf("personalAssistantSettingsCanBeResolved", "isSupportPA")
        }.toList().createHooks {
            returnConstant(true)
        }

//        "needHideMinusScreen"
        deviceConfig.methodFinder().filter {
            name in setOf(
                "needHideThemeManager",
            )
        }.toList().createHooks {
            returnConstant(false)
        }

//        "isSupportHideAndShowStatusBar"
        deviceConfig.methodFinder().filter {
            name in setOf(
                "isSystemSupportHotSeatsBlur",
                "checkSystemIsSupportHotSeatsBlur",
            )
        }.toList().createHooks {
            returnConstant(true)
        }
    }
}