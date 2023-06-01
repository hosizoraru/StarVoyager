package star.sky.voyager.hook.hooks.systemui

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Typeface
import android.os.BatteryManager
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.ConstructorFinder.`-Static`.constructorFinder
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.api.isPad
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getBoolean
import star.sky.voyager.utils.key.hasEnable
import kotlin.math.abs

@SuppressLint("StaticFieldLeak")
object StatusBarBattery : HookRegister() {
    var textview: TextView? = null
    var context: Context? = null

    @SuppressLint("SetTextI18n")
    override fun init() = hasEnable("system_ui_show_status_bar_battery") {
        loadClass("com.android.systemui.statusbar.phone.DarkIconDispatcherImpl").methodFinder()
            .first {
                name == "applyIconTint"
            }.createHook {
                after {
                    val color = it.thisObject.getObjectFieldAs<Int>("mIconTint")
                    if (textview != null) {
                        textview!!.setTextColor(color)
                    }
                }
            }

        loadClass("com.android.systemui.statusbar.phone.MiuiPhoneStatusBarView").constructorFinder()
            .first {
                parameterCount == 2
            }.createHook {
                after {
                    context = it.args[0] as Context
                }
            }

        loadClass("com.android.systemui.statusbar.phone.MiuiPhoneStatusBarView").methodFinder()
            .first {
                name == "onFinishInflate"
            }.createHook {
                after {
                    val mStatusBarLeftContainer =
                        it.thisObject.getObjectFieldAs<LinearLayout>("mStatusBarLeftContainer")
                    textview = TextView(context).apply {
                        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 8f)
                        typeface = Typeface.DEFAULT_BOLD
                        isSingleLine = false
                        setLineSpacing(0F, 0.8F)
                        setPadding(8, 0, 0, 0)
                    }
                    val frameLayout = context?.let { it1 -> FrameLayout(it1) }
                    val params = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.gravity = Gravity.CENTER_VERTICAL
                    textview!!.layoutParams = params
                    if (isPad()) {
                        params.topMargin = 7 // 调整上边距
                    }
                    frameLayout?.addView(textview)
                    mStatusBarLeftContainer.addView(frameLayout)

                    context!!.registerReceiver(
                        BatteryReceiver(),
                        IntentFilter().apply { addAction(Intent.ACTION_BATTERY_CHANGED) })
                }
            }

    }

    class BatteryReceiver : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            val any = getBoolean("show_status_bar_battery_any", false)
            val mA = getBoolean("current_mA", false)
            val current: Double
            val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            val temperature = (intent.getIntExtra("temperature", 0) / 10.0)
            current = if (mA) {
                abs(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW) / 1000.0)
            } else {
                abs(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW) / 1000 / 1000.0)
            }
            val status = intent.getIntExtra("status", 0)
            if (textview !== null) {
                if (any) {
                    textview!!.text = if (mA) {
                        "${"%.0f".format(current)}mA\n${"%.1f".format(temperature)}℃"
                    } else {
                        "${"%.2f".format(current)}A\n${"%.1f".format(temperature)}℃"
                    }
                    textview!!.visibility = View.VISIBLE
                } else {
                    if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                        textview!!.text = if (mA) {
                            "${"%.0f".format(current)}mA\n${"%.1f".format(temperature)}℃"
                        } else {
                            "${"%.2f".format(current)}A\n${"%.1f".format(temperature)}℃"
                        }
                        textview!!.visibility = View.VISIBLE
                    } else {
                        textview!!.visibility = View.GONE
                    }
                }
            }
        }
    }
}