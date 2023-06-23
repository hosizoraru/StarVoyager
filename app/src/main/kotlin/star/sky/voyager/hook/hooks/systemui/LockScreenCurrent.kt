package star.sky.voyager.hook.hooks.systemui

import android.app.AndroidAppHelper
import android.content.Context
import android.os.BatteryManager
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.moduleRes
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.R
import star.sky.voyager.utils.api.getObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import java.io.BufferedReader
import java.io.FileReader
import kotlin.math.abs

object LockScreenCurrent : HookRegister() {
    override fun init() = hasEnable("lock_screen_charging_current") {
        loadClass("com.android.keyguard.charge.ChargeUtils").methodFinder()
            .filterByName("getChargingHintText")
            .filterByParamCount(3)
            .first().createHook {
                after {
                    it.result = "${getCurrent()}\n${it.result}"
                }
            }

        loadClass("com.android.systemui.statusbar.phone.KeyguardBottomAreaView").methodFinder()
            .filterByName("onFinishInflate")
            .first().createHook {
                after {
                    (it.thisObject.getObjectField("mIndicationText") as TextView).isSingleLine =
                        false
                }
            }
    }

    private fun getCurrent(): String {
        val batteryManager = AndroidAppHelper.currentApplication()
            .getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val current =
            abs(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW) / 1000)
        return "${moduleRes.getString(R.string.current_current)} ${current}mA"
    }

    /**
     * 获取平均电流值
     * 获取 filePath 文件 totalCount 次数的平均值，每次采样间隔 intervalMs 时间
     */
    private fun getMeanCurrentVal(filePath: String, totalCount: Int, intervalMs: Int): Float {
        var meanVal = 0.0f
        if (totalCount <= 0) {
            return 0.0f
        }
        for (i in 0 until totalCount) {
            runCatching {
                val f: Float = readFile(filePath, 0).toFloat()
                meanVal += f / totalCount
            }
            if (intervalMs <= 0) {
                continue
            }
            runCatching {
                Thread.sleep(intervalMs.toLong())
            }
        }
        return meanVal
    }

    private fun readFile(path: String, defaultValue: Int): Int {
        runCatching {
            val bufferedReader = BufferedReader(FileReader(path))
            val i: Int = bufferedReader.readLine().toInt(10)
            bufferedReader.close()
            return i
        }
        return defaultValue
    }
}