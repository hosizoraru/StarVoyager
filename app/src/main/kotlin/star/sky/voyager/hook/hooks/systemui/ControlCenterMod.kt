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
import star.sky.voyager.utils.api.hookPluginClassLoader
import star.sky.voyager.utils.api.isPad
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable

object ControlCenterMod : HookRegister() {
    private var clockSize: Float = 0.0f
    private var dateSize: Float = 0.0f
//    private var carrierSize: Float = 0.0f

    override fun init() {
        val controlCenterStatusBarClass =
            loadClass("com.android.systemui.controlcenter.phone.widget.ControlCenterStatusBar")
        if (!isPad()) {
            clockSize = getInt("control_center_clock_size", 133).toFloat()
            dateSize = getInt("control_center_date_size", 43).toFloat()
//            carrierSize = getInt("control_center_carrier_size", 37).toFloat()
        } else {
            clockSize = getInt("control_center_clock_size", 98).toFloat()
            dateSize = getInt("control_center_date_size", 32).toFloat()
//            carrierSize = getInt("control_center_carrier_size", 30).toFloat()
        }
        val clockColor = parseColor(getString("control_center_clock_color", "#FFFFFF"))
        val dateColor = parseColor(getString("control_center_date_color", "#FFFFFF"))
//        val carrierColor = parseColor(getString("control_center_carrier_color", "#FFFFFF"))
        hookPluginClassLoader { appInfo, classLoader ->
            loadClass(
                "miui.systemui.controlcenter.windowview.MainPanelHeaderController",
                classLoader
            ).methodFinder().first {
                name == "updateClocksAppearance"
            }.createHook {
                after { param ->
                    val miuiClockView = param.thisObject.getObjectFieldAs<TextView>("clockView")
                    val miuiDateView = param.thisObject.getObjectFieldAs<TextView>("dateView")

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

                    Log.i("Control Center Clock:  ${miuiClockView.textSize}  ${miuiClockView.typeface}  ${miuiClockView.textColors}")
                    Log.i("Control Center Date:  ${miuiDateView.textSize}  ${miuiDateView.typeface}  ${miuiDateView.textColors}")
                }
            }
//            controlCenterStatusBarClass.methodFinder()
//                .first {
//                    name == "updateFlaresInfo"
//                }.createHook {
//                    after { param ->
//                        val miuiCarrierView =
//                            param.thisObject.getObjectFieldAs<TextView>("carrierText")
//                        hasEnable("control_center_carrier_font") {
//                            miuiCarrierView.typeface = Typeface.DEFAULT
//                        }
//                        hasEnable("control_center_carrier_bold") {
//                            miuiCarrierView.typeface = Typeface.DEFAULT_BOLD
//                        }
//                        miuiCarrierView.setTextSize(TypedValue.COMPLEX_UNIT_SHIFT, carrierSize)
//                        miuiCarrierView.setTextColor(carrierColor)
//                        Log.i("Control Center Carrier: ${miuiCarrierView.textSize}  ${miuiCarrierView.typeface}  ${miuiCarrierView.textColors}")
//                    }
//                }
        }
    }
}