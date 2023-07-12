package star.sky.voyager.hook.hooks.home

import android.app.Application
import com.github.kyuubiran.ezxhelper.ClassUtils.invokeStaticMethodBestMatch
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object EnablePerfectIcons : HookRegister() {
    override fun init() = hasEnable("enable_perfect_icons") {
        val clazzMiuiSettingsUtils =
            loadClass("com.miui.launcher.utils.MiuiSettingsUtils")

        loadClass("com.miui.home.launcher.Application").methodFinder()
            .filterByName("disablePerfectIcons")
            .first().createHook {
                replace {
                    val contentResolver =
                        (it.thisObject as Application).contentResolver
                    clazzMiuiSettingsUtils.methodFinder()
                        .filterByName("putBool")
                    invokeStaticMethodBestMatch(
                        clazzMiuiSettingsUtils,
                        "putBooleanToSystem",
                        null,
                        contentResolver,
                        "key_miui_mod_icon_enable",
                        true
                    )
                }
            }

        loadClass("com.miui.home.library.compat.LauncherActivityInfoCompat").methodFinder()
            .filterByName("getIconResource")
            .first().createHook {
                returnConstant(0)
            }
    }
}