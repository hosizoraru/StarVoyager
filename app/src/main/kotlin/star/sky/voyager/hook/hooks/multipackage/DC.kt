package star.sky.voyager.hook.hooks.multipackage

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.FeatureParserCls

object DC : HookRegister() {
    override fun init() = hasEnable("dc_backlight") {
        when (hostPackageName) {
            "com.xiaomi.misettings" -> {
                FeatureParserCls.methodFinder()
                    .filterByName("getBoolean")
                    .toList().createHooks {
                        before {
                            if (it.args[0] == "support_dc_backlight") {
                                it.result = true
                            }
                            if (it.args[0] == "support_dc_backlight_sec") {
                                it.result = true
                            }
                        }
                    }
            }

            "com.android.settings" -> {
                setOf(
                    loadClass("com.android.settings.development.LowFlickerBacklightController"),
                    loadClass("com.android.settings.display.DcLightPreferenceController")
                ).forEach {
                    it.methodFinder()
                        .filterByName("isAvailable")
                        .first().createHook {
                            returnConstant(true)
                        }
                }
            }
        }
    }
}