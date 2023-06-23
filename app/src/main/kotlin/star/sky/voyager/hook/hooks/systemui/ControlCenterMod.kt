package star.sky.voyager.hook.hooks.systemui

import android.graphics.Color.parseColor
import android.graphics.Typeface
import android.util.TypedValue
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.hookPluginClassLoader

object ControlCenterMod : HookRegister() {
    private var clockSize: Float = 0.0f
    private var dateSize: Float = 0.0f
    private var carrierSize: Float = 0.0f
    override fun init() = hasEnable("control_center_mod") {
        val controlCenterStatusBarClass =
            loadClass("com.android.systemui.controlcenter.phone.widget.ControlCenterStatusBar")
        val clockColor = parseColor(getString("control_center_clock_color", "#FFFFFF"))
        val dateColor = parseColor(getString("control_center_date_color", "#FFFFFF"))
        val carrierColor = parseColor(getString("control_center_carrier_color", "#FFFFFF"))
        hookPluginClassLoader { _, classLoader ->
            loadClass(
                "miui.systemui.controlcenter.windowview.MainPanelHeaderController",
                classLoader
            ).methodFinder()
                .filterByName("updateClocksAppearance")
                .first().createHook {
                    after { param ->
                        val miuiClockView = param.thisObject.getObjectFieldAs<TextView>("clockView")
                        val miuiDateView = param.thisObject.getObjectFieldAs<TextView>("dateView")

                        clockSize = getInt(
                            "control_center_clock_size",
                            miuiClockView.textSize.toInt()
                        ).toFloat()
                        dateSize = getInt(
                            "control_center_date_size",
                            miuiDateView.textSize.toInt()
                        ).toFloat()

                        Log.ix("Control Center Clock:  ${miuiClockView.textSize}")
                        Log.ix("Control Center Date:  ${miuiDateView.textSize}")

                        // 修改字体
                        hasEnable("control_center_clock_font") {
                            miuiClockView.typeface = Typeface.DEFAULT
                        }
                        hasEnable("control_center_date_font") {
                            miuiDateView.typeface = Typeface.DEFAULT
                        }
                        hasEnable("control_center_clock_bold") {
                            miuiClockView.typeface = Typeface.DEFAULT_BOLD
                        }
                        hasEnable("control_center_date_bold") {
                            miuiDateView.typeface = Typeface.DEFAULT_BOLD
                        }

                        // 修改字体大小
                        miuiClockView.setTextSize(TypedValue.COMPLEX_UNIT_SHIFT, clockSize)
                        miuiDateView.setTextSize(TypedValue.COMPLEX_UNIT_SHIFT, dateSize)

                        // 修改颜色
                        miuiClockView.setTextColor(clockColor)
                        miuiDateView.setTextColor(dateColor)

//                        Log.i("Control Center Clock:  ${miuiClockView.textSize}  ${miuiClockView.typeface}  ${miuiClockView.textColors}")
//                        Log.i("Control Center Date:  ${miuiDateView.textSize}  ${miuiDateView.typeface}  ${miuiDateView.textColors}")
                    }
                }
        }

        controlCenterStatusBarClass.methodFinder()
            .filterByName("onDensityOrFontScaleChanged")
            .first().createHook {
                after { param ->
                    val miuiCarrierView =
                        param.thisObject.getObjectFieldAs<TextView>("carrierText")
                    carrierSize = getInt(
                        "control_center_carrier_size",
                        miuiCarrierView.textSize.toInt()
                    ).toFloat()
                    Log.ix("Control Center Carrier: ${miuiCarrierView.textSize}")
                    hasEnable("control_center_carrier_font") {
                        miuiCarrierView.typeface = Typeface.DEFAULT
                    }
                    hasEnable("control_center_carrier_bold") {
                        miuiCarrierView.typeface = Typeface.DEFAULT_BOLD
                    }
                    miuiCarrierView.setTextSize(TypedValue.COMPLEX_UNIT_SHIFT, carrierSize)
                    miuiCarrierView.setTextColor(carrierColor)
//                        Log.i("Control Center Carrier: ${miuiCarrierView.textSize}  ${miuiCarrierView.typeface}  ${miuiCarrierView.textColors}")
                }
            }
    }
}