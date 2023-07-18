package star.sky.voyager.hook.hooks.systemui

import android.graphics.Color.parseColor
import android.view.View
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import com.github.kyuubiran.ezxhelper.ObjectUtils.setObject
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XposedBridge.invokeOriginalMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.SetKeyMap.setKeyMap

object MeterIconView : HookRegister() {
    override fun init() = hasEnable("meter_battery_color") {
        val meterIconViewCls =
            loadClass("com.android.systemui.statusbar.views.MiuiBatteryMeterIconView")

        meterIconViewCls.methodFinder()
            .filterByName("updateResources")
            .first().createHook {
                after { param ->
                    param.result = invokeOriginalMethod(param.method, param.thisObject, param.args)
                    val batteryMeterIconView = param.thisObject as View

                    setKeyMap(
                        mapOf(
                            "meter_low_level_color_key" to {
                                val lowBatteryColor =
                                    parseColor(getString("meter_low_level_color", "#FFC800"))
                                setObject(
                                    batteryMeterIconView,
                                    "mBatteryLowColor",
                                    lowBatteryColor
                                )
                            },
                            "meter_power_save_color_key" to {
                                val powerSaveColor =
                                    parseColor(getString("meter_power_save_color", "#B4FFB0"))
                                setObject(
                                    batteryMeterIconView,
                                    "mBatteryPowerSaveColor",
                                    powerSaveColor
                                )
                            },
                            "meter_performance_mode_color_key" to {
                                val performanceModeColor =
                                    parseColor(getString("meter_performance_mode_color", "#FF838F"))
                                setObject(
                                    batteryMeterIconView,
                                    "mBatteryPerformanceModeColor",
                                    performanceModeColor
                                )
                            },
                            "meter_charging_color_key" to {
                                val chargingColor =
                                    parseColor(getString("meter_charging_color", "#FF5A40"))
                                setObject(
                                    batteryMeterIconView,
                                    "mBatteryChargingColor",
                                    chargingColor
                                )
                            },
                            "meter_normal_light_color_key" to {
                                val normalDigitLightColor =
                                    parseColor(getString("meter_normal_light_color", "#E5FF00"))
                                setObject(
                                    batteryMeterIconView,
                                    "mBatteryNormalDigitLightColor",
                                    normalDigitLightColor
                                )
                            },
                            "meter_normal_dark_color_key" to {
                                val normalDigitDarkColor =
                                    parseColor(getString("meter_normal_dark_color", "#232526"))
                                setObject(
                                    batteryMeterIconView,
                                    "mBatteryNormalDigitDarkColor",
                                    normalDigitDarkColor
                                )
                            }
                        )
                    )

                    // 更新电池图标的显示
//                    batteryMeterIconView.callMethod("onDarkChangeInternal")
                    invokeMethodBestMatch(batteryMeterIconView, "onDarkChangeInternal")
                }
            }
    }
}

//Log.i(
//"low level color: ${
//ObjectUtils.getObjectOrNull(
//batteryMeterIconView,
//"mBatteryLowColor"
//)
//}"
//)
//Log.i(
//"power save color: ${
//ObjectUtils.getObjectOrNull(
//batteryMeterIconView,
//"mBatteryPowerSaveColor"
//)
//}"
//)
//Log.i(
//"performance mode color: ${
//ObjectUtils.getObjectOrNull(
//batteryMeterIconView,
//"mBatteryPerformanceModeColor"
//)
//}"
//)
//Log.i(
//"charging color: ${
//ObjectUtils.getObjectOrNull(
//batteryMeterIconView,
//"mBatteryChargingColor"
//)
//}"
//)
//Log.i(
//"normal digit light color: ${
//ObjectUtils.getObjectOrNull(
//batteryMeterIconView,
//"mBatteryNormalDigitLightColor"
//)
//}"
//)
//Log.i(
//"normal digit dark color: ${
//ObjectUtils.getObjectOrNull(
//batteryMeterIconView,
//"mBatteryNormalDigitDarkColor"
//)
//}"
//)