package star.sky.voyager.activity.pages.sub

import android.annotation.SuppressLint
import android.view.View
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R

@SuppressLint("NonConstantResourceId")
@BMPage("status_bar_battery", titleId = R.string.status_bar_battery, hideMenu = false)
//@BMPage("status_bar_battery", "Status Bar Battery", hideMenu = false)
class StatusBarBatteryPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.battery_percentage_font_size,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.battery_percentage_font_size)
                        setMessage(R.string.zero_do_no_change)
                        setEditText(
                            "", "${activity.getString(R.string.current)}${
                                safeSP.getFloat("battery_percentage_font_size", 0f)
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "battery_percentage_font_size",
                                    getEditText().toFloat()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                })
        )
        val batteryColorBinding = GetDataBinding({
            safeSP.getBoolean(
                "meter_battery_color",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.color_custom
            ), SwitchV("meter_battery_color", dataBindingSend = batteryColorBinding.bindingSend)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.low_level_color
            ),
            SwitchV("meter_low_level_color_key"),
            dataBindingRecv = batteryColorBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.low_level_color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.low_level_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "meter_low_level_color",
                                    "#FFC800"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "meter_low_level_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = batteryColorBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.power_save_color
            ),
            SwitchV("meter_power_save_color_key"),
            dataBindingRecv = batteryColorBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.power_save_color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.power_save_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "meter_power_save_color",
                                    "#B4FFB0"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "meter_power_save_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = batteryColorBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.performance_mode_color
            ),
            SwitchV("meter_performance_mode_color_key"),
            dataBindingRecv = batteryColorBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.performance_mode_color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.performance_mode_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "meter_performance_mode_color",
                                    "#FF838F"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "meter_performance_mode_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = batteryColorBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.charging_color
            ),
            SwitchV("meter_charging_color_key"),
            dataBindingRecv = batteryColorBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.charging_color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.charging_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "meter_charging_color",
                                    "#FF5A40"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "meter_charging_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = batteryColorBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.normal_light_color
            ),
            SwitchV("meter_normal_light_color_key"),
            dataBindingRecv = batteryColorBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.normal_light_color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.normal_light_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "meter_normal_light_color",
                                    "#E5FF00"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "meter_normal_light_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = batteryColorBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.normal_dark_color
            ),
            SwitchV("meter_normal_dark_color_key"),
            dataBindingRecv = batteryColorBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.normal_dark_color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.normal_dark_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "meter_normal_dark_color",
                                    "#232526"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "meter_normal_dark_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = batteryColorBinding.binding.getRecv(1)
        )
    }
}