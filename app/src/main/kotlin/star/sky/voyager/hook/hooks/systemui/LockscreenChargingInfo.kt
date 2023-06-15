package star.sky.voyager.hook.hooks.systemui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.PowerManager
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.EzXHelper.moduleRes
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.R
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getBoolean
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable
import java.io.BufferedReader
import java.io.FileReader

object LockscreenChargingInfo : HookRegister() {
    override fun init() = hasEnable("lockscreen_charging_info") {
        val refreshFrequency =
            getInt("lockscreen_charging_info_refresh_frequency", 10).toLong() * 100
        val clazzDependency =
            loadClass("com.android.systemui.Dependency")
        val clazzKeyguardIndicationController =
            loadClass("com.android.systemui.statusbar.KeyguardIndicationController")
        loadClassOrNull("com.android.systemui.statusbar.phone.KeyguardIndicationTextView")?.constructors?.createHooks {
            after { param ->
                (param.thisObject as TextView).isSingleLine = false
                val screenOnOffReceiver = object : BroadcastReceiver() {
                    val keyguardIndicationController = ClassUtils.invokeStaticMethodBestMatch(
                        clazzDependency, "get", null, clazzKeyguardIndicationController
                    )!!
                    val handler = Handler((param.thisObject as TextView).context.mainLooper)
                    val runnable = object : Runnable {
                        override fun run() {
                            ObjectUtils.invokeMethodBestMatch(
                                keyguardIndicationController,
                                "updatePowerIndication"
                            )
                            handler.postDelayed(this, refreshFrequency)
                        }
                    }

                    init {
                        if (((param.thisObject as TextView).context.getSystemService(Context.POWER_SERVICE) as PowerManager).isInteractive) {
                            handler.post(runnable)
                        }
                    }

                    override fun onReceive(context: Context, intent: Intent) {
                        when (intent.action) {
                            Intent.ACTION_SCREEN_ON -> {
                                handler.post(runnable)
                            }

                            Intent.ACTION_SCREEN_OFF -> {
                                handler.removeCallbacks(runnable)
                            }
                        }
                    }
                }

                val filter = IntentFilter().apply {
                    addAction(Intent.ACTION_SCREEN_ON)
                    addAction(Intent.ACTION_SCREEN_OFF)
                }
                (param.thisObject as TextView).context.registerReceiver(screenOnOffReceiver, filter)
            }
        }
        loadClass("com.android.keyguard.charge.ChargeUtils").methodFinder()
            .filterByName("getChargingHintText")
            .filterByParamTypes(
                Context::class.java,
                Boolean::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            )
            .first().createHook {
                after { param ->
                    param.result = param.result?.let { "$it\n${getChargingInfo()}" }
                }
            }
    }

    private fun getChargingInfo(): String {
        return kotlin.runCatching {
            val current =
                readDoubleFromFile("/sys/class/power_supply/battery/current_now")?.let { -it / 1000000.0 }
            val voltage =
                readDoubleFromFile("/sys/class/power_supply/battery/voltage_now")?.let { it / 1000000.0 }
            val temperature =
                readDoubleFromFile("/sys/class/power_supply/battery/temp")?.let { it / 10.0 }
            val watt = current?.let { cur -> voltage?.let { volt -> cur * volt } }

            formatChargingInfo(current, voltage, watt, temperature)
        }.getOrElse { moduleRes.getString(R.string.lockscreen_charging_info_not_supported) }
    }

    private fun String.readFile(): String? = kotlin.runCatching {
        BufferedReader(FileReader(this)).use { it.readLine() }
    }.getOrNull()

    private fun readDoubleFromFile(filePath: String): Double? {
        return filePath.readFile()?.toDoubleOrNull()
    }

    private fun formatChargingInfo(
        current: Double?,
        voltage: Double?,
        watt: Double?,
        temperature: Double?
    ): String {
        return current?.let { currentVal ->
            voltage?.let { voltageVal ->
                watt?.let { wattVal ->
                    temperature?.let { temperatureVal ->
                        val formattedCurrent =
                            if (currentVal < 10) "%.0fm".format(currentVal * 1000) else "%.2f".format(
                                currentVal
                            )
                        val formattedVoltage = "%.2f".format(voltageVal)
                        val formattedWatt = "%.2f".format(wattVal)
                        val formattedTemperature = "%.1f℃".format(temperatureVal)

                        if (getBoolean("current_mA", false)) {
                            "$formattedCurrent A · $formattedVoltage V · $formattedWatt W\n$formattedTemperature"
                        } else {
                            String.format(
                                "%.2f A · %.2f V · %.2f W\n%.1f ℃",
                                currentVal,
                                voltageVal,
                                wattVal,
                                temperatureVal
                            )
                        }
                    }
                }
            }
        } ?: moduleRes.getString(R.string.lockscreen_charging_info_not_supported)
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