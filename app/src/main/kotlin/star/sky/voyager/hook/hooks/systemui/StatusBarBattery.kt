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
    private lateinit var textview: TextView
    private val leftPaddingPx: Int by lazy {
        calculatePaddingPx("status_bar_battery_left_padding", 8)
    }

    private val rightPaddingPx: Int by lazy {
        calculatePaddingPx("status_bar_battery_right_padding", 0, 2)
    }

    private val topPaddingPx: Int by lazy {
        calculatePaddingPx("status_bar_battery_top_padding", 0)
    }

    private val bottomPaddingPx: Int by lazy {
        calculatePaddingPx("status_bar_battery_bottom_padding", 0)
    }
    private var color: Int? = null

    override fun init() = hasEnable("system_ui_show_status_bar_battery") {
        val userColor = getString("status_bar_battery_text_color", "#0d84ff")
        val useCustomColor = getBoolean("status_bar_battery_text_color_custom_enable", false)
        val textSize = getInt("status_bar_battery_text_size", 8).toFloat()

        val lineSpacingAdd = getInt("status_bar_battery_line_spacing_add", 0).toFloat()
        val lineSpacingMulti = getInt("status_bar_battery_line_spacing_multi", 80).toFloat() / 100

        val leftMargining = getInt("status_bar_battery_left_margining", if (IS_TABLET) 1 else -7)
        val rightMargining = getInt("status_bar_battery_right_margining", 0)
        val topMargining = getInt("status_bar_battery_top_margining", if (IS_TABLET) -20 else 0)
        val bottomMargining = getInt("status_bar_battery_bottom_margining", 0)

        val miuiPhoneStatusBarViewClass =
            loadClass("com.android.systemui.statusbar.phone.MiuiPhoneStatusBarView")

        miuiPhoneStatusBarViewClass.constructorFinder()
            .filterByParamCount(2)
            .first().createHook {
                after {
                    appContext = it.args[0] as Context
                }
            }

        miuiPhoneStatusBarViewClass.methodFinder()
            .filterByName("onFinishInflate")
            .first().createHook {
                after { param ->
                    val mStatusBarLeftContainer =
                        param.thisObject.getObjectFieldAs<LinearLayout>("mStatusBarLeftContainer")
                    textview = TextView(appContext).apply {
                        setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize)
                        typeface = Typeface.DEFAULT_BOLD
                        isSingleLine = false
                        setLineSpacing(lineSpacingAdd, lineSpacingMulti)
                        setPadding(
                            leftPaddingPx,
                            topPaddingPx,
                            rightPaddingPx,
                            bottomPaddingPx
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
                        BatteryReceiver(textview),
                        IntentFilter().apply { addAction(Intent.ACTION_BATTERY_CHANGED) })

                    loadClass("com.android.systemui.statusbar.phone.DarkIconDispatcherImpl").methodFinder()
                        .filterByName("applyIconTint")
                        .first().createHook {
                            after { param ->
                                color = if (useCustomColor && userColor?.isNotEmpty() == true) {
                                    parseColor(userColor)
                                } else {
                                    param.thisObject.getObjectFieldAs<Int>("mIconTint")
                                }
                                color?.let { textview.setTextColor(it) }
                            }
                        }
                }
            }
    }

    class BatteryReceiver(private val textView: TextView) : BroadcastReceiver() {
        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            val any = getBoolean("show_status_bar_battery_any", false)
            val mA = getBoolean("current_mA", false)
            val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            val temperature = intent.getFloatExtra("temperature", 0.0F) / 10.0
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

            textView.also {
                it.visibility =
                    if (any || batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING) View.VISIBLE else View.GONE
            }.takeIf { it.visibility == View.VISIBLE }?.let {
                it.text = "$currentText\n${temperatureFormat}℃"
            }
        }
    }

    private fun calculatePaddingPx(
        key: String,
        defaultPadding: Int,
        tabletPadding: Int = 0
    ): Int {
        val paddingDp = getInt(key, if (IS_TABLET) tabletPadding else defaultPadding).toFloat()
        return (paddingDp * appContext.resources.displayMetrics.density).toInt()
    }
}

