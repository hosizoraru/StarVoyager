package star.sky.voyager.hook.hooks.multipackage

import com.github.kyuubiran.ezxhelper.ClassUtils.getStaticObjectOrNullAs
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object BatteryStyle : HookRegister() {
    private var notch: Boolean? = null
    override fun init() = hasEnable("battery_style") {
        when (hostPackageName) {
            "com.android.settings" -> {
                val statusBarUtilsCls =
                    loadClass("com.android.settings.utils.StatusBarUtils")
                val miuiStatusBarSettingsCls =
                    loadClass("com.android.settings.MiuiStatusBarSettings")
                val notchStatusBarSettingsCls =
                    loadClass("com.android.settings.NotchStatusBarSettings")

                notch =
                    getStaticObjectOrNullAs<Boolean>(
                        statusBarUtilsCls,
                        "IS_NOTCH"
                    )!!

                setOf(miuiStatusBarSettingsCls, notchStatusBarSettingsCls).forEach { clazz ->
                    clazz.methodFinder()
                        .filterByName("setupBatteryIndicator")
                        .toList().createHooks {
                            before {
                                setStaticObject(statusBarUtilsCls, "IS_NOTCH", false)
                            }

                            after {
                                setStaticObject(statusBarUtilsCls, "IS_NOTCH", notch)
                            }
                        }
                }
            }

            "com.android.systemui" -> {
                val deviceConfigCls =
                    loadClass("com.miui.systemui.DeviceConfig")
                val miuiBatteryControllerImplCls =
                    loadClass("com.android.systemui.statusbar.policy.MiuiBatteryControllerImpl")

                notch = getStaticObjectOrNullAs<Boolean>(
                    deviceConfigCls,
                    "IS_NOTCH"
                )!!

                miuiBatteryControllerImplCls.methodFinder()
                    .filterByName("setBatteryStyle")
                    .first().createHook {
                        before {
                            setStaticObject(deviceConfigCls, "IS_NOTCH", false)
                        }

                        after {
                            setStaticObject(deviceConfigCls, "IS_NOTCH", notch)
                        }
                    }
            }
        }
    }
}