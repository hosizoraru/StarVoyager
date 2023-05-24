package star.sky.voyager.activity.pages

import android.view.View
import android.widget.Switch
import android.widget.Toast
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SpinnerV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R

@BMPage("scope_system_ui", "System UI", hideMenu = false)
class SystemUIPage : BasePage() {
    override fun onCreate() {
        val monetBinding = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
                "monet_theme",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.monet_theme,
            ), SwitchV("monet_theme", false, dataBindingSend = monetBinding.bindingSend)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.your_theme_accent_color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.your_theme_accent_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                MIUIActivity.safeSP.getString("your_theme_accent_color", "#0d84ff")
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                MIUIActivity.safeSP.putAny(
                                    "your_theme_accent_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = monetBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.your_theme_neutral_color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.your_theme_neutral_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                MIUIActivity.safeSP.getString("your_theme_neutral_color", "#A6CDE7")
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                MIUIActivity.safeSP.putAny(
                                    "your_theme_neutral_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = monetBinding.binding.getRecv(1)
        )
//        TextSummaryWithSpinner(
//            TextSummaryV(
//                textId = R.string.your_theme_style,
//            ),
//            SpinnerV(
//                MIUIActivity.safeSP.getString("your_theme_style", "TONAL_SPOT"),
//                dropDownWidth = 190F
//            ) {
//                add("TONAL_SPOT") {
//                    MIUIActivity.safeSP.putAny("your_theme_style", "TONAL_SPOT")
//                }
//                add("SPRITZ") {
//                    MIUIActivity.safeSP.putAny("your_theme_style", "SPRITZ")
//                }
//                add("VIBRANT") {
//                    MIUIActivity.safeSP.putAny("your_theme_style", "VIBRANT")
//                }
//                add("EXPRESSIVE") {
//                    MIUIActivity.safeSP.putAny("your_theme_style", "EXPRESSIVE")
//                }
//                add("RAINBOW") {
//                    MIUIActivity.safeSP.putAny("your_theme_style", "RAINBOW")
//                }
//                add("FRUIT_SALAD") {
//                    MIUIActivity.safeSP.putAny("your_theme_style", "FRUIT_SALAD")
//                }
//                add("CONTENT") {
//                    MIUIActivity.safeSP.putAny("your_theme_style", "CONTENT")
//                }
//            }, dataBindingRecv = monetBinding.binding.getRecv(1)
//        )
        Line()
        TitleText(textId = R.string.status_bar)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.double_tap_to_sleep),
            SwitchV("status_bar_double_tap_to_sleep")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_ui_show_status_bar_battery,
                tipsId = R.string.system_ui_show_status_bar_battery_summary
            ),
            SwitchV("system_ui_show_status_bar_battery", false)
        )
        Line()
        TitleText(textId = R.string.status_bar_layout)
        val statusBarLayoutMode: HashMap<Int, String> = hashMapOf<Int, String>().also {
            it[0] = getString(R.string.default1)
            it[1] = getString(R.string.clock_center)
            it[2] = getString(R.string.clock_right)
            it[3] = getString(R.string.clock_center_and_icon_left)
        }
        TextWithSpinner(
            TextV(textId = R.string.status_bar_layout_mode),
            SpinnerV(
                statusBarLayoutMode[MIUIActivity.safeSP.getInt(
                    "status_bar_layout_mode",
                    0
                )].toString()
            ) {
                add(statusBarLayoutMode[0].toString()) {
                    MIUIActivity.safeSP.putAny("status_bar_layout_mode", 0)
                }
                add(statusBarLayoutMode[1].toString()) {
                    MIUIActivity.safeSP.putAny("status_bar_layout_mode", 1)
                }
                add(statusBarLayoutMode[2].toString()) {
                    MIUIActivity.safeSP.putAny("status_bar_layout_mode", 2)
                }
                add(statusBarLayoutMode[3].toString()) {
                    MIUIActivity.safeSP.putAny("status_bar_layout_mode", 3)
                }
            })

        val layoutCompatibilityBinding = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
                "layout_compatibility_mode", false
            )
        }) { view, flags, data ->
            when (flags) {
                1 -> (view as Switch).isEnabled = data as Boolean
                2 -> view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
            }
        }


        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.layout_compatibility_mode,
                tipsId = R.string.layout_compatibility_mode_summary
            ),
            SwitchV(
                "layout_compatibility_mode",
                dataBindingSend = layoutCompatibilityBinding.bindingSend
            )
        )

        Text(
            textId = R.string.left_margin,
            dataBindingRecv = layoutCompatibilityBinding.binding.getRecv(2)
        )
        SeekBarWithText(
            "status_bar_left_margin",
            0,
            300,
            0,
            dataBindingRecv = layoutCompatibilityBinding.binding.getRecv(2)
        )
        Text(
            textId = R.string.right_margin,
            dataBindingRecv = layoutCompatibilityBinding.binding.getRecv(2)
        )
        SeekBarWithText(
            "status_bar_right_margin",
            0,
            300,
            0,
            dataBindingRecv = layoutCompatibilityBinding.binding.getRecv(2)
        )
        Line()
        TitleText(textId = R.string.status_bar_clock_format)


        val customClockPresetBinding = GetDataBinding({
            MIUIActivity.safeSP.getInt(
                "custom_clock_mode",
                0
            ) == 1
        }) { view, flags, data ->
            when (flags) {
                1 -> (view as Switch).isEnabled = data as Boolean
                2 -> view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
            }
        }

        val customClockGeekBinding =
            GetDataBinding({ MIUIActivity.safeSP.getInt("custom_clock_mode", 0) == 2 }

            ) { view, flags, data ->
                when (flags) {
                    1 -> (view as Switch).isEnabled = data as Boolean
                    2 -> view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
                }
            }

        val customClockMode: HashMap<Int, String> = hashMapOf<Int, String>().also {
            it[0] = getString(R.string.off)
            it[1] = getString(R.string.preset)
            it[2] = getString(R.string.geek)
        }
        TextWithSpinner(
            TextV(textId = R.string.custom_clock_mode),
            SpinnerV(
                customClockMode[MIUIActivity.safeSP.getInt(
                    "custom_clock_mode",
                    0
                )].toString()
            ) {
                add(customClockMode[0].toString()) {
                    MIUIActivity.safeSP.putAny("custom_clock_mode", 0)
                    customClockPresetBinding.binding.Send().send(false)
                    customClockGeekBinding.binding.Send().send(false)
                }
                add(customClockMode[1].toString()) {
                    MIUIActivity.safeSP.putAny("custom_clock_mode", 1)
                    customClockPresetBinding.binding.Send().send(true)
                    customClockGeekBinding.binding.Send().send(false)
                }
                add(customClockMode[2].toString()) {
                    MIUIActivity.safeSP.putAny("custom_clock_mode", 2)
                    customClockPresetBinding.binding.Send().send(false)
                    customClockGeekBinding.binding.Send().send(true)
                }
            })

        //预设模式起始
        TextWithSwitch(
            TextV(textId = R.string.status_bar_time_year),
            SwitchV("status_bar_time_year"),
            dataBindingRecv = customClockPresetBinding.binding.getRecv(2)
        )
        TextWithSwitch(
            TextV(textId = R.string.status_bar_time_month),
            SwitchV("status_bar_time_month"),
            dataBindingRecv = customClockPresetBinding.binding.getRecv(2)
        )
        TextWithSwitch(
            TextV(textId = R.string.status_bar_time_day),
            SwitchV("status_bar_time_day"),
            dataBindingRecv = customClockPresetBinding.binding.getRecv(2)
        )
        TextWithSwitch(
            TextV(textId = R.string.status_bar_time_week),
            SwitchV("status_bar_time_week"),
            dataBindingRecv = customClockPresetBinding.binding.getRecv(2)
        )
        TextWithSwitch(
            TextV(textId = R.string.status_bar_time_double_hour),
            SwitchV("status_bar_time_double_hour"),
            dataBindingRecv = customClockPresetBinding.binding.getRecv(2)
        )
        TextWithSwitch(
            TextV(textId = R.string.status_bar_time_period),
            SwitchV("status_bar_time_period", true),
            dataBindingRecv = customClockPresetBinding.binding.getRecv(2)
        )
        TextWithSwitch(
            TextV(textId = R.string.status_bar_time_seconds),
            SwitchV("status_bar_time_seconds"),
            dataBindingRecv = customClockPresetBinding.binding.getRecv(2)
        )
        TextWithSwitch(
            TextV(textId = R.string.status_bar_time_hide_space),
            SwitchV("status_bar_time_hide_space"),
            dataBindingRecv = customClockPresetBinding.binding.getRecv(2)
        )
        TextWithSwitch(
            TextV(textId = R.string.status_bar_time_double_line),
            SwitchV("status_bar_time_double_line"),
            dataBindingRecv = customClockPresetBinding.binding.getRecv(2)
        )
        TextWithSwitch(
            TextV(textId = R.string.status_bar_time_double_line_center_align),
            SwitchV("status_bar_time_double_line_center_align"),
            dataBindingRecv = customClockPresetBinding.binding.getRecv(2)
        )
        Text(
            textId = R.string.status_bar_clock_size,
            dataBindingRecv = customClockPresetBinding.binding.getRecv(2)
        )
        SeekBarWithText(
            "status_bar_clock_size",
            0,
            18,
            0,
            dataBindingRecv = customClockPresetBinding.binding.getRecv(2)
        )
        Text(
            textId = R.string.status_bar_clock_double_line_size,
            dataBindingRecv = customClockPresetBinding.binding.getRecv(2)
        )
        SeekBarWithText(
            "status_bar_clock_double_line_size",
            0,
            9,
            0,
            dataBindingRecv = customClockPresetBinding.binding.getRecv(2)
        )
        //预设模式结束

        //极客模式起始
        TextSummaryWithArrow(TextSummaryV(textId = R.string.custom_clock_format_geek) {
            MIUIDialog(activity) {
                setTitle(R.string.custom_clock_format_geek)
                setEditText(
                    MIUIActivity.safeSP.getString("custom_clock_format_geek", "HH:mm:ss"),
                    "",
                    isSingleLine = false
                )
                setLButton(textId = R.string.cancel) {
                    dismiss()
                }
                setRButton(textId = R.string.done) {
                    if (getEditText().isNotEmpty()) {
                        try {
                            MIUIActivity.safeSP.putAny("custom_clock_format_geek", getEditText())
                            dismiss()
                            return@setRButton
                        } catch (_: Throwable) {
                        }
                    }
                    Toast.makeText(activity, R.string.input_error, Toast.LENGTH_SHORT)
                        .show()
                }
            }.show()
        }, dataBindingRecv = customClockGeekBinding.binding.getRecv(2))
        TextWithSwitch(
            TextV(textId = R.string.status_bar_time_double_line_center_align),
            SwitchV("status_bar_time_center_align_geek"),
            dataBindingRecv = customClockGeekBinding.binding.getRecv(2)
        )
        Text(
            textId = R.string.status_bar_clock_size,
            dataBindingRecv = customClockGeekBinding.binding.getRecv(2)
        )
        SeekBarWithText(
            "status_bar_clock_size_geek",
            0,
            18,
            0,
            dataBindingRecv = customClockGeekBinding.binding.getRecv(2)
        )
        //极客模式结束


        Line()
        TitleText(textId = R.string.status_bar_icon)
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.hide_icon,
                onClickListener = { showFragment("hide_icon") }
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.icon_position,
                onClickListener = { showFragment("icon_position") }
            )
        )
        TextWithSwitch(
            TextV(textId = R.string.show_wifi_standard),
            SwitchV("show_wifi_standard")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_ui_use_new_hd,
                tipsId = R.string.system_ui_use_new_summary
            ),
            SwitchV("system_ui_use_new_hd", false)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.battery_percentage_font_size,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.battery_percentage_font_size)
                        setMessage(R.string.zero_do_no_change)
                        setEditText(
                            "", "${activity.getString(R.string.current)}${
                                MIUIActivity.safeSP.getFloat("battery_percentage_font_size", 0f)
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                MIUIActivity.safeSP.putAny(
                                    "battery_percentage_font_size",
                                    getEditText().toFloat()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                })
        )
        val customMobileTypeTextBinding = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
                "custom_mobile_type_text_switch",
                false
            )
        }) { view, flags, data ->
            when (flags) {
                1 -> (view as Switch).isEnabled = data as Boolean
                2 -> view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
            }
        }
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.custom_mobile_type_text_switch),
            SwitchV(
                "custom_mobile_type_text_switch",
                dataBindingSend = customMobileTypeTextBinding.bindingSend
            )
        )
        TextSummaryWithArrow(TextSummaryV(textId = R.string.custom_mobile_type_text) {
            MIUIDialog(activity) {
                setTitle(R.string.custom_mobile_type_text)
                setEditText(MIUIActivity.safeSP.getString("custom_mobile_type_text", "5G"), "")
                setLButton(textId = R.string.cancel) {
                    dismiss()
                }
                setRButton(textId = R.string.done) {
                    if (getEditText().isNotEmpty()) {
                        try {
                            MIUIActivity.safeSP.putAny("custom_mobile_type_text", getEditText())
                            dismiss()
                            return@setRButton
                        } catch (_: Throwable) {
                        }
                    }
                    Toast.makeText(activity, R.string.input_error, Toast.LENGTH_SHORT)
                        .show()
                }
            }.show()
        }, dataBindingRecv = customMobileTypeTextBinding.binding.getRecv(2))
        val bigMobileTypeIconBinding = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
                "big_mobile_type_icon",
                false
            )
        }) { view, flags, data ->
            when (flags) {
                1 -> (view as Switch).isEnabled = data as Boolean
                2 -> view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
            }
        }
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.big_mobile_type_icon),
            SwitchV(
                "big_mobile_type_icon",
                dataBindingSend = bigMobileTypeIconBinding.bindingSend
            )
        )
        val bigMobileTypeLocation: HashMap<Int, String> = hashMapOf<Int, String>().also {
            it[0] = getString(R.string.left)
            it[1] = getString(R.string.right)
        }
        TextWithSpinner(
            TextV(textId = R.string.big_mobile_type_location),
            SpinnerV(
                bigMobileTypeLocation[MIUIActivity.safeSP.getInt(
                    "big_mobile_type_location",
                    1
                )].toString()
            ) {
                add(bigMobileTypeLocation[0].toString()) {
                    MIUIActivity.safeSP.putAny("big_mobile_type_location", 0)
                }
                add(bigMobileTypeLocation[1].toString()) {
                    MIUIActivity.safeSP.putAny("big_mobile_type_location", 1)
                }
            },
            dataBindingRecv = bigMobileTypeIconBinding.binding.getRecv(2)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.big_mobile_type_only_show_network_card),
            SwitchV("big_mobile_type_only_show_network_card", false),
            dataBindingRecv = bigMobileTypeIconBinding.binding.getRecv(2)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.big_mobile_type_icon_bold),
            SwitchV("big_mobile_type_icon_bold", true),
            dataBindingRecv = bigMobileTypeIconBinding.binding.getRecv(2)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.big_mobile_type_icon_size,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.big_mobile_type_icon_size)
                        setEditText(
                            "",
                            "${activity.getString(R.string.def)}12.5, ${activity.getString(R.string.current)}${
                                MIUIActivity.safeSP.getFloat("big_mobile_type_icon_size", 12.5f)
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                MIUIActivity.safeSP.putAny(
                                    "big_mobile_type_icon_size",
                                    getEditText().toFloat()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = bigMobileTypeIconBinding.binding.getRecv(2)
        )
        TextSummaryWithArrow(TextSummaryV(textId = R.string.big_mobile_type_icon_up_and_down_position) {
            MIUIDialog(activity) {
                setTitle(R.string.big_mobile_type_icon_up_and_down_position)
                setMessage("${activity.getString(R.string.range)} -15~15")
                setEditText(
                    "",
                    "${activity.getString(R.string.def)}0, ${activity.getString(R.string.current)}${
                        MIUIActivity.safeSP.getInt("big_mobile_type_icon_up_and_down_position", 0)
                    }"
                )
                setLButton(textId = R.string.cancel) {
                    dismiss()
                }
                setRButton(textId = R.string.done) {
                    if (getEditText().isNotEmpty()) {
                        try {
                            val value = getEditText().toInt()
                            if (value in (-15..15)) {
                                MIUIActivity.safeSP.putAny(
                                    "big_mobile_type_icon_up_and_down_position",
                                    value
                                )
                                dismiss()
                                return@setRButton
                            }
                        } catch (_: Throwable) {
                        }
                    }
                    Toast.makeText(activity, R.string.input_error, Toast.LENGTH_SHORT)
                        .show()
                }
            }.show()
        }, dataBindingRecv = bigMobileTypeIconBinding.binding.getRecv(2))
        TextSummaryWithArrow(TextSummaryV(textId = R.string.big_mobile_type_icon_left_and_right_margins) {
            MIUIDialog(activity) {
                setTitle(R.string.big_mobile_type_icon_left_and_right_margins)
                setMessage("${activity.getString(R.string.range)} 0~30")
                setEditText(
                    "",
                    "${activity.getString(R.string.def)}0, ${activity.getString(R.string.current)}${
                        MIUIActivity.safeSP.getInt("big_mobile_type_icon_left_and_right_margins", 0)
                    }"
                )
                setLButton(textId = R.string.cancel) {
                    dismiss()
                }
                setRButton(textId = R.string.done) {
                    if (getEditText().isNotEmpty()) {
                        try {
                            val value = getEditText().toInt()
                            if (value in (0..30)) {
                                MIUIActivity.safeSP.putAny(
                                    "big_mobile_type_icon_left_and_right_margins",
                                    value
                                )
                                dismiss()
                                return@setRButton
                            }
                        } catch (_: Throwable) {
                        }
                    }
                    Toast.makeText(activity, R.string.input_error, Toast.LENGTH_SHORT)
                        .show()
                }
            }.show()
        }, dataBindingRecv = bigMobileTypeIconBinding.binding.getRecv(2))
        Text(textId = R.string.maximum_number_of_notification_icons)
        SeekBarWithText("maximum_number_of_notification_icons", 1, 20, 3)
        Text(textId = R.string.maximum_number_of_notification_dots)
        SeekBarWithText("maximum_number_of_notification_dots", 0, 4, 3)
        Text(textId = R.string.maximum_number_of_lockscreen_notification_icons)
        SeekBarWithText("maximum_number_of_lockscreen_notification_icons", 1, 20, 3)
        Line()
        TitleText(textId = R.string.status_bar_network_speed)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.status_bar_network_speed_refresh_speed,
                tipsId = R.string.status_bar_network_speed_refresh_speed_summary
            ), SwitchV("status_bar_network_speed_refresh_speed")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.hide_status_bar_network_speed_second,
                tipsId = R.string.hide_status_bar_network_speed_second_summary
            ), SwitchV("hide_status_bar_network_speed_second")
        )
        TextWithSwitch(
            TextV(textId = R.string.hide_network_speed_splitter),
            SwitchV("hide_network_speed_splitter")
        )
        val statusBarDualRowNetworkSpeedBinding = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
                "status_bar_dual_row_network_speed",
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
                textId = R.string.status_bar_dual_row_network_speed,
                tipsId = R.string.status_bar_dual_row_network_speed_summary
            ),
            SwitchV(
                "status_bar_dual_row_network_speed",
                dataBindingSend = statusBarDualRowNetworkSpeedBinding.bindingSend
            )
        )
        val align: HashMap<Int, String> = hashMapOf()
        align[0] = getString(R.string.left)
        align[1] = getString(R.string.right)
        TextWithSpinner(
            TextV(textId = R.string.status_bar_network_speed_dual_row_gravity),
            SpinnerV(
                align[MIUIActivity.safeSP.getInt(
                    "status_bar_network_speed_dual_row_gravity",
                    0
                )].toString()
            ) {
                add(align[0].toString()) {
                    MIUIActivity.safeSP.putAny("status_bar_network_speed_dual_row_gravity", 0)
                }
                add(align[1].toString()) {
                    MIUIActivity.safeSP.putAny("status_bar_network_speed_dual_row_gravity", 1)

                }
            },
            dataBindingRecv = statusBarDualRowNetworkSpeedBinding.binding.getRecv(2)
        )
        TextWithSpinner(
            TextV(textId = R.string.status_bar_network_speed_dual_row_icon),
            SpinnerV(
                MIUIActivity.safeSP.getString(
                    "status_bar_network_speed_dual_row_icon",
                    getString(R.string.none)
                )
            ) {
                add(getString(R.string.none)) {
                    MIUIActivity.safeSP.putAny(
                        "status_bar_network_speed_dual_row_icon",
                        getString(R.string.none)
                    )
                }
                add("▲▼") {
                    MIUIActivity.safeSP.putAny("status_bar_network_speed_dual_row_icon", "▲▼")
                }
                add("△▽") {
                    MIUIActivity.safeSP.putAny("status_bar_network_speed_dual_row_icon", "△▽")
                }
                add("↑↓") {
                    MIUIActivity.safeSP.putAny("status_bar_network_speed_dual_row_icon", "↑↓")
                }
            })
        Text(
            textId = R.string.status_bar_network_speed_dual_row_size,
            dataBindingRecv = statusBarDualRowNetworkSpeedBinding.binding.getRecv(2)
        )
        SeekBarWithText(
            "status_bar_network_speed_dual_row_size",
            0,
            9,
            0,
            dataBindingRecv = statusBarDualRowNetworkSpeedBinding.binding.getRecv(2)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.hide_slow_speed_network_speed,
            ),
            SwitchV(
                "hide_slow_speed_network_speed",
            )
        )
        SeekBarWithText("slow_speed_degree", 0, 200, 1)
        Line()
        TitleText(textId = R.string.notification_center)
        val showWeatherMainSwitchBinding = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
                "notification_weather",
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
                colorId = R.color.blue
            ),
            SwitchV(
                "notification_weather",
                dataBindingSend = showWeatherMainSwitchBinding.bindingSend
            )
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.show_city,
            ),
            SwitchV("notification_weather_city"),
            dataBindingRecv = showWeatherMainSwitchBinding.binding.getRecv(2)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.can_notification_slide,
                tipsId = R.string.can_notification_slide_summary,
            ),
            SwitchV("can_notification_slide"),
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.notification_settings_no_white_list
            ),
            SwitchV("notification_settings_no_white_list", false)
        )
        Line()
        TitleText(textId = R.string.control_center)
        // TODO: 控制中心天气在A13未修复，暂不可用
        val controlCenterWeatherBinding = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
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
                tipsId = R.string.Disable_Bluetooth_summary,
            ),
            SwitchV("Disable_Bluetooth"),
        )
        Line()
        TitleText(textId = R.string.lock_screen)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.lock_screen_clock_display_seconds,
                tipsId = R.string.only_official_default_themes_are_supported
            ), SwitchV("lock_screen_clock_display_seconds")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.lock_screen_clock_use_system_font,
                tipsId = R.string.only_official_default_themes_are_supported
            ), SwitchV("lock_screen_clock_use_system_font")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.lock_screen_date_use_system_font,
                tipsId = R.string.only_official_default_themes_are_supported
            ), SwitchV("lock_screen_date_use_system_font")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.blur_lock_screen_button,
                tipsId = R.string.only_official_default_themes_are_supported
            ), SwitchV("blur_lock_screen_button")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.remove_the_left_side_of_the_lock_screen,
                tipsId = R.string.only_official_default_themes_are_supported
            ), SwitchV("remove_the_left_side_of_the_lock_screen")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.remove_lock_screen_camera,
                tipsId = R.string.only_official_default_themes_are_supported
            ), SwitchV("remove_lock_screen_camera")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.enable_wave_charge_animation),
            SwitchV("enable_wave_charge_animation")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.lock_screen_charging_current,
                tipsId = R.string.only_official_default_themes_are_supported
            ), SwitchV("lock_screen_charging_current")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.double_tap_to_sleep,
                tipsId = R.string.home_double_tap_to_sleep_summary
            ), SwitchV("lock_screen_double_tap_to_sleep")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.no_need_to_enter_password_when_power_on,
                tipsId = R.string.no_need_to_enter_password_when_power_on_summary
            ), SwitchV("no_need_to_enter_password_when_power_on")
        )
        Line()
        TitleText(textId = R.string.old_quick_settings_panel)
        val oldQSCustomSwitchBinding = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
                "old_qs_custom_switch",
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
                textId = R.string.old_qs_custom_switch,
                colorId = R.color.blue
            ),
            SwitchV(
                "old_qs_custom_switch",
                dataBindingSend = oldQSCustomSwitchBinding.bindingSend
            )
        )
        Text(
            textId = R.string.qs_custom_rows,
            dataBindingRecv = oldQSCustomSwitchBinding.binding.getRecv(2)
        )
        SeekBarWithText(
            "qs_custom_rows",
            1,
            6,
            3,
            dataBindingRecv = oldQSCustomSwitchBinding.binding.getRecv(2)
        )
        Text(
            textId = R.string.qs_custom_rows_horizontal,
            dataBindingRecv = oldQSCustomSwitchBinding.binding.getRecv(2)
        )
        SeekBarWithText(
            "qs_custom_rows_horizontal",
            1,
            3,
            2,
            dataBindingRecv = oldQSCustomSwitchBinding.binding.getRecv(2)
        )
        Text(
            textId = R.string.qs_custom_columns,
            dataBindingRecv = oldQSCustomSwitchBinding.binding.getRecv(2)
        )
        SeekBarWithText(
            "qs_custom_columns",
            1,
            7,
            4,
            dataBindingRecv = oldQSCustomSwitchBinding.binding.getRecv(2)
        )
        Text(
            textId = R.string.qs_custom_columns_unexpanded,
            dataBindingRecv = oldQSCustomSwitchBinding.binding.getRecv(2)
        )
        SeekBarWithText(
            "qs_custom_columns_unexpanded",
            1,
            7,
            5,
            dataBindingRecv = oldQSCustomSwitchBinding.binding.getRecv(2)
        )
        Line()
        TitleText(textId = R.string.scope_rear_display)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.show_weather_main_switch,
            ), SwitchV("rear_show_weather")
        )
    }
}