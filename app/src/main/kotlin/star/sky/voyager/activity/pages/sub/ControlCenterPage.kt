package star.sky.voyager.activity.pages.sub

import android.view.View
import android.widget.Switch
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SeekBarWithTextV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R
import star.sky.voyager.utils.voyager.LineB.lineB
import star.sky.voyager.utils.yife.Build.IS_TABLET

@BMPage("control_center", "Control Center", hideMenu = false)
class ControlCenterPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.control_center)
        // TODO: 控制中心天气在A13未修复，暂不可用
        val controlCenterWeatherBinding = GetDataBinding({
            safeSP.getBoolean(
                "control_center_weather",
                false
            )
        }) { view, flags, data ->
            when (flags) {
                1 -> (view as Switch).isEnabled = data as Boolean
                2 -> view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
            }
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.show_weather_main_switch,
                colorId = R.color.blue,
                tipsId = R.string.already_can_not_use,
            ),
            SwitchV(
                "control_center_weather",
                dataBindingSend = controlCenterWeatherBinding.bindingSend
            )
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.show_city,
            ),
            SwitchV("control_center_weather_city"),
            dataBindingRecv = controlCenterWeatherBinding.binding.getRecv(2)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.restore_near_by_tile
            ),
            SwitchV("restore_near_by_tile", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.Disable_Bluetooth,
                tipsId = R.string.Disable_Bluetooth_summary
            ),
            SwitchV("Disable_Bluetooth"),
        )
        Line()
        val ccBinding = GetDataBinding({
            safeSP.getBoolean(
                "control_center_mod",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.control_center_mod,
                tipsId = R.string.control_center_mod_tips
            ),
            SwitchV(
                "control_center_mod",
                false,
                dataBindingSend = ccBinding.bindingSend
            )
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.text_size_def_log,
            ), SwitchV("text_size_def_log", false),
            dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        lineB(ccBinding.binding.getRecv(1))
        TitleText(textId = R.string.time)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.hide,
            ), SwitchV("control_center_clock", false),
            dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_font,
            ), SwitchV("control_center_clock_font", false),
            dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_font_bold,
            ), SwitchV("control_center_clock_bold", false),
            dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.font_size_custom,
            ), SwitchV("control_center_clock_size_custom", false),
            dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        if (IS_TABLET) {
            TextWithSeekBar(
                TextV(textId = R.string.font_size),
                SeekBarWithTextV("control_center_clock_size", 50, 150, 98),
                dataBindingRecv = ccBinding.binding.getRecv(1)
            )
        } else {
            TextWithSeekBar(
                TextV(textId = R.string.font_size),
                SeekBarWithTextV("control_center_clock_size", 80, 200, 133),
                dataBindingRecv = ccBinding.binding.getRecv(1)
            )
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.color_custom,
            ), SwitchV("control_center_clock_color_custom", false),
            dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.control_center_clock_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "control_center_clock_color",
                                    "#FFFFFF"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "control_center_clock_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        lineB(ccBinding.binding.getRecv(1))
        TitleText(textId = R.string.date)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.hide,
            ), SwitchV("control_center_date", false),
            dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_font,
            ), SwitchV("control_center_date_font", false),
            dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_font_bold,
            ), SwitchV("control_center_date_bold", false),
            dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.font_size_custom,
            ), SwitchV("control_center_date_size_custom", false),
            dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        if (IS_TABLET) {
            TextWithSeekBar(
                TextV(textId = R.string.font_size),
                SeekBarWithTextV("control_center_date_size", 10, 64, 32),
                dataBindingRecv = ccBinding.binding.getRecv(1)
            )
        } else {
            TextWithSeekBar(
                TextV(textId = R.string.font_size),
                SeekBarWithTextV("control_center_date_size", 20, 86, 43),
                dataBindingRecv = ccBinding.binding.getRecv(1)
            )
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.color_custom,
            ), SwitchV("control_center_date_color_custom", false),
            dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.control_center_date_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "control_center_date_color",
                                    "#FFFFFF"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "control_center_date_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        lineB(ccBinding.binding.getRecv(1))
        TitleText(textId = R.string.carrier_info)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.hide,
            ), SwitchV("control_center_carrier", false),
            dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_font,
            ), SwitchV("control_center_carrier_font", false),
            dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_font_bold,
            ), SwitchV("control_center_carrier_bold", false),
            dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.font_size_custom,
            ), SwitchV("control_center_carrier_size_custom", false),
            dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        if (!IS_TABLET) {
            TextWithSeekBar(
                TextV(textId = R.string.font_size),
                SeekBarWithTextV("control_center_carrier_size", 10, 74, 37),
                dataBindingRecv = ccBinding.binding.getRecv(1)
            )
        } else {
            TextWithSeekBar(
                TextV(textId = R.string.font_size),
                SeekBarWithTextV("control_center_carrier_size", 10, 60, 30),
                dataBindingRecv = ccBinding.binding.getRecv(1)
            )
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.color_custom,
            ), SwitchV("control_center_carrier_color_custom", false),
            dataBindingRecv = ccBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.control_center_carrier_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "control_center_carrier_color",
                                    "#FFFFFF"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "control_center_carrier_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = ccBinding.binding.getRecv(1)
        )
    }
}