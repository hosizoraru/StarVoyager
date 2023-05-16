package star.sky.voyager.hook.hooks.systemui

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Typeface
import android.os.BatteryManager
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.ConstructorFinder.`-Static`.constructorFinder
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import kotlin.math.abs

@SuppressLint("StaticFieldLeak")
object StatusBarBattery : HookRegister() {
    var textview: TextView? = null
    var context: Context? = null

    @SuppressLint("SetTextI18n")
    override fun init() = hasEnable("systemui_show_statusbar_battery") {
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
                mStatusBarLeftContainer.addView(textview)

                context!!.registerReceiver(
                    BatteryReceiver(),
                    IntentFilter().apply { addAction(Intent.ACTION_BATTERY_CHANGED) })
            }
        }
    }

    class BatteryReceiver : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            val temperature = (intent.getIntExtra("temperature", 0) / 10.0)
            val current =
                abs(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW) / 1000 / 1000.0)
            val status = intent.getIntExtra("status", 0)
            if (textview !== null) {
                if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                    textview!!.text = "${"%.2f".format(current)}A\n${"%.1f".format(temperature)}℃"
                    textview!!.visibility = View.VISIBLE
                } else {
                    textview!!.visibility = View.GONE
                }
            }
        }
    }
}