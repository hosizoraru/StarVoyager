package star.sky.voyager.hook.hooks.systemui

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color.parseColor
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
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getBoolean
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.Build.IS_TABLET
import kotlin.math.abs

@SuppressLint("StaticFieldLeak")
object StatusBarBattery : HookRegister() {
    private lateinit var appContext: Context
    var textview: TextView? = null
    private var leftPaddingPx: Int? = 0
    private var rightPaddingPx: Int? = 0
    private var topPaddingPx: Int? = 0
    private var bottomPaddingPx: Int? = 0
    private var color: Int? = 0

    override fun init() = hasEnable("system_ui_show_status_bar_battery") {
        val userColor = getString("status_bar_battery_text_color", "#0d84ff")
        val useCustomColor = getBoolean("status_bar_battery_text_color_custom_enable", false)
        val textSize = getInt("status_bar_battery_text_size", 8).toFloat()

        val lineSpacingAdd = getInt("status_bar_battery_line_spacing_add", 0).toFloat()
        val lineSpacingMulti = getInt("status_bar_battery_line_spacing_multi", 80).toFloat() / 100

        val leftPadding =
            getInt("status_bar_battery_left_padding", if (IS_TABLET) 0 else 8).toFloat()
        val rightPadding =
            getInt("status_bar_battery_right_padding", if (IS_TABLET) 2 else 0).toFloat()
        val topPadding = getInt("status_bar_battery_top_padding", 0).toFloat()
        val bottomPadding = getInt("status_bar_battery_bottom_padding", 0).toFloat()

        val leftMargining = getInt("status_bar_battery_left_margining", if (IS_TABLET) 1 else -7)
        val rightMargining = getInt("status_bar_battery_right_margining", 0)
        val topMargining = getInt("status_bar_battery_top_margining", if (IS_TABLET) -20 else 0)
        val bottomMargining = getInt("status_bar_battery_bottom_margining", 0)

        val miuiPhoneStatusBarViewClass =
            loadClass("com.android.systemui.statusbar.phone.MiuiPhoneStatusBarView")
        loadClass("com.android.systemui.statusbar.phone.DarkIconDispatcherImpl").methodFinder()
            .filterByName("applyIconTint")
            .first().createHook {
                after { it ->
                    color = if (useCustomColor && userColor?.isNotEmpty() == true) {
                        parseColor(userColor)
                    } else {
                        it.thisObject.getObjectFieldAs<Int>("mIconTint")
                    }
                    if (textview != null) {
                        color?.let { textview?.setTextColor(it) }
                    }
                }
            }

        miuiPhoneStatusBarViewClass.constructorFinder()
            .filterByParamCount(2)
            .first().createHook {
                after {
                    appContext = it.args[0] as Context
                    with(appContext.resources.displayMetrics.density) {
                        leftPaddingPx = (leftPadding * this).toInt()
                        rightPaddingPx = (rightPadding * this).toInt()
                        topPaddingPx = (topPadding * this).toInt()
                        bottomPaddingPx = (bottomPadding * this).toInt()
                    }
                }
            }

        miuiPhoneStatusBarViewClass.methodFinder()
            .filterByName("onFinishInflate")
            .first().createHook {
                after {
                    val mStatusBarLeftContainer =
                        it.thisObject.getObjectFieldAs<LinearLayout>("mStatusBarLeftContainer")
                    textview = TextView(appContext).apply {
                        setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize)
                        typeface = Typeface.DEFAULT_BOLD
                        isSingleLine = false
                        setLineSpacing(lineSpacingAdd, lineSpacingMulti)
                        setPadding(
                            leftPaddingPx ?: 0,
                            topPaddingPx ?: 0,
                            rightPaddingPx ?: 0,
                            bottomPaddingPx ?: 0
                        )
                        layoutParams = FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.WRAP_CONTENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            leftMargin = leftMargining
                            rightMargin = rightMargining
                            topMargin = topMargining
                            bottomMargin = bottomMargining
                            gravity = Gravity.CENTER_VERTICAL
                        }
                    }
                    mStatusBarLeftContainer.addView(appContext.let {
                        FrameLayout(it).apply {
                            layoutParams = FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.WRAP_CONTENT,
                                FrameLayout.LayoutParams.WRAP_CONTENT
                            ).apply {
                                leftMargin = leftMargining
                                rightMargin = rightMargining
                                topMargin = topMargining
                                bottomMargin = bottomMargining
                                gravity = Gravity.CENTER_VERTICAL
                            }
                            addView(textview)
                        }
                    })

                    appContext.registerReceiver(
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
            val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            val temperature = intent.getIntExtra("temperature", 0) / 10.0
            val batteryCurrentNow =
                batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW)
                    .toDouble()
            val current = abs(if (mA) batteryCurrentNow / 1000 else batteryCurrentNow / 1_000_000)

            val currentFormat = if (mA) {
                if (current < 10_000) "%.0f mA" else "%.2f A"
            } else {
                "%.2f A"
            }

            val currentText =
                String.format(currentFormat, if (current < 10_000) current else current / 1_000)

            val batteryStatus = intent.getIntExtra("status", 0)
            val temperatureFormat = "%.1f".format(temperature)

            textview?.apply {
                visibility =
                    if (any || batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING) View.VISIBLE else View.GONE
                if (visibility == View.VISIBLE) {
                    text = "$currentText\n${temperatureFormat}℃"
                }
            }
        }
    }
}