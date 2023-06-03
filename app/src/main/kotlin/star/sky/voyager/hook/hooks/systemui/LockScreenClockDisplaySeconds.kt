package star.sky.voyager.hook.hooks.systemui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.ConstructorFinder.`-Static`.constructorFinder
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XC_MethodHook
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import java.lang.reflect.Method
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Timer
import java.util.TimerTask

object LockScreenClockDisplaySeconds : HookRegister() {

    private var nowTime: Date = Calendar.getInstance().time

    override fun init() = hasEnable("lock_screen_clock_display_seconds") {
        loadClass("com.miui.clock.MiuiBaseClock").constructorFinder()
            .filterByParamCount(2)
            .first().createHook {
                after {
                    try {
                        val viewGroup = it.thisObject as LinearLayout
                        val d: Method = viewGroup.javaClass.getDeclaredMethod("updateTime")
                        val r = Runnable {
                            d.isAccessible = true
                            d.invoke(viewGroup)
                        }

                        class T : TimerTask() {
                            override fun run() {
                                Handler(viewGroup.context.mainLooper).post(r)
                            }
                        }
                        Timer().scheduleAtFixedRate(
                            T(),
                            1000 - System.currentTimeMillis() % 1000,
                            1000
                        )
                    } catch (_: Exception) {
                    }
                }
            }
        loadClass("com.miui.clock.MiuiLeftTopClock").methodFinder()
            .filterByName("updateTime")
            .first().createHook {
                after {
                    updateTime(it, false)
                }
            }
        loadClass("com.miui.clock.MiuiCenterHorizontalClock").methodFinder()
            .filterByName("updateTime")
            .first().createHook {
                after {
                    updateTime(it, false)
                }
            }
        loadClass("com.miui.clock.MiuiLeftTopLargeClock").methodFinder()
            .filterByName("updateTime")
            .first().createHook {
                after {
                    updateTime(it, false)
                }
            }
        loadClass("com.miui.clock.MiuiVerticalClock").methodFinder()
            .filterByName("updateTime")
            .first().createHook {
                after {
                    updateTime(it, true)
                }
            }
    }

    private fun updateTime(it: XC_MethodHook.MethodHookParam, isVertical: Boolean) {
        val textV = it.thisObject.getObjectFieldAs<TextView>("mTimeText")
        val c: Context = textV.context

        Log.d(
            "lock_screen_clock_display_seconds",
            "updateTime: ${it.thisObject.javaClass.simpleName}"
        )
        val is24 = Settings.System.getString(c.contentResolver, Settings.System.TIME_12_24) == "24"

        nowTime = Calendar.getInstance().time

        textV.text = getTime(is24, isVertical)

    }


    @SuppressLint("SimpleDateFormat")
    private fun getTime(is24: Boolean, isVertical: Boolean): String {
        var timePattern = ""
        timePattern += if (isVertical) { //垂直
            if (is24) "HH\nmm\nss" else "hh\nmm\nss"
        } else { //水平
            if (is24) "HH:mm:ss" else "h:mm:ss"
        }
        timePattern = SimpleDateFormat(timePattern).format(nowTime)
        return timePattern
    }
}