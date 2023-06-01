package star.sky.voyager.hook.hooks.systemui

import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.EzXHelper
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.R
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getBoolean
import star.sky.voyager.utils.key.hasEnable
import java.io.BufferedReader
import java.io.FileReader

object LockscreenChargingInfo : HookRegister() {
    override fun init() = hasEnable("lockscreen_charging_info") {
        loadClassOrNull("com.android.systemui.statusbar.phone.KeyguardIndicationTextView")?.constructors?.createHooks {
            after {
                (it.thisObject as TextView).isSingleLine = false
            }
        }
        loadClass("com.android.keyguard.charge.ChargeUtils").methodFinder()
            .filterByName("getChargingHintText")
            .filterByParamCount(3)
            .first().createHook {
                after {
                    if (it.result != null) {
                        it.result = it.result as String + "\n" + getChargingInfo()
                    }
                }
            }
    }

    private fun getChargingInfo(): String {
        kotlin.runCatching {
            var current = 0.0
            var voltage = 0.0
            var temperature = 0.0
            val watt: Double by lazy {
                current * voltage
            }

            current =
                FileReader("/sys/class/power_supply/battery/current_now").use { fileReader ->
                    BufferedReader(fileReader).use { bufferedReader ->
                        -1.0 * bufferedReader.readLine().toDouble() / 1000000.0
                    }
                }
            voltage = FileReader("/sys/class/power_supply/battery/voltage_now").use { fileReader ->
                BufferedReader(fileReader).use { bufferedReader ->
                    bufferedReader.readLine().toDouble() / 1000000.0
                }
            }
            temperature =
                FileReader("/sys/class/power_supply/battery/temp").use { fileReader ->
                    BufferedReader(fileReader).use { bufferedReader ->
                        bufferedReader.readLine().toDouble() / 10.0
                    }
                }

            // val clazzMiuiChargeManager = loadClass("com.android.keyguard.charge.MiuiChargeManager")
            // val plugState = loadClass("com.android.systemui.Dependency").classHelper()
            //     .invokeStaticMethodBestMatch("get", null, clazzMiuiChargeManager)!!.objectHelper()
            //     .getObjectOrNull("mBatteryStatus")!!.objectHelper().getObjectOrNullAs<Int>("wireState")
            // when (plugState) {
            //     10 -> {
            //         current =
            //             FileReader("/sys/class/power_supply/wireless/rx_iout").use { fileReader ->
            //                 BufferedReader(fileReader).use { bufferedReader ->
            //                     bufferedReader.readLine().toDouble() / 1000000.0
            //                 }
            //             }
            //         voltage = FileReader("/sys/class/power_supply/wireless/input_voltage_vrect").use { fileReader ->
            //             BufferedReader(fileReader).use { bufferedReader ->
            //                 bufferedReader.readLine().toDouble() / 1000000.0
            //             }
            //         }
            //     }
            //
            //     else -> {
            //         current =
            //             FileReader("/sys/class/power_supply/usb/input_current_now").use { fileReader ->
            //                 BufferedReader(fileReader).use { bufferedReader ->
            //                     bufferedReader.readLine().toDouble() / 1000000.0
            //                 }
            //             }
            //         voltage = FileReader("/sys/class/power_supply/usb/voltage_now").use { fileReader ->
            //             BufferedReader(fileReader).use { bufferedReader ->
            //                 bufferedReader.readLine().toDouble() / 1000000.0
            //             }
            //         }
            //     }
            // }
            if (getBoolean("current_mA", false)) {
                return "${
                    if (current < 10) String.format(
                        "%.0fm",
                        current * 1000
                    ) else "%.2f".format(current)
                }A · %.2fV · %.2fW\n%.1f℃".format(voltage, watt, temperature)
            } else {
                return String.format(
                    "%.2f A · %.2f V · %.2f W\n%.1f ℃",
                    current,
                    voltage,
                    watt,
                    temperature
                )
            }
        }
        return EzXHelper.moduleRes.getString(R.string.lockscreen_charging_info_not_supported)
    }
}