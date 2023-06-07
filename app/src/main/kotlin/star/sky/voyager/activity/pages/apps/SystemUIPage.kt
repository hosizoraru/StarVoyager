package star.sky.voyager.activity.pages.apps

import android.view.View
import android.widget.Switch
import android.widget.Toast
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
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
            safeSP.getBoolean(
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
                                safeSP.getString("your_theme_accent_color", "#0d84ff")
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
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
                                safeSP.getString("your_theme_neutral_color", "#A6CDE7")
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
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
//                safeSP.getString("your_theme_style", "TONAL_SPOT"),
//                dropDownWidth = 190F
//            ) {
//                add("TONAL_SPOT") {
//                    safeSP.putAny("your_theme_style", "TONAL_SPOT")
//                }
//                add("SPRITZ") {
//                    safeSP.putAny("your_theme_style", "SPRITZ")
//                }
//                add("VIBRANT") {
//                    safeSP.putAny("your_theme_style", "VIBRANT")
//                }
//                add("EXPRESSIVE") {
//                    safeSP.putAny("your_theme_style", "EXPRESSIVE")
//                }
//                add("RAINBOW") {
//                    safeSP.putAny("your_theme_style", "RAINBOW")
//                }
//                add("FRUIT_SALAD") {
//                    safeSP.putAny("your_theme_style", "FRUIT_SALAD")
//                }
//                add("CONTENT") {
//                    safeSP.putAny("your_theme_style", "CONTENT")
//                }
//            }, dataBindingRecv = monetBinding.binding.getRecv(1)
//        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.notification_center,
                onClickListener = { showFragment("notification_center") }
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.control_center,
                onClickListener = { showFragment("control_center") }
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.lock_screen,
                onClickListener = { showFragment("lock_screen") }
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.status_bar,
                onClickListener = { showFragment("status_bar") }
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.status_bar_icon,
                onClickListener = { showFragment("status_bar_icon") }
            )
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
                statusBarLayoutMode[safeSP.getInt(
                    "status_bar_layout_mode",
                    0
                )].toString(),
                dropDownWidth = 225F
            ) {
                add(statusBarLayoutMode[0].toString()) {
                    safeSP.putAny("status_bar_layout_mode", 0)
                }
                add(statusBarLayoutMode[1].toString()) {
                    safeSP.putAny("status_bar_layout_mode", 1)
                }
                add(statusBarLayoutMode[2].toString()) {
                    safeSP.putAny("status_bar_layout_mode", 2)
                }
                add(statusBarLayoutMode[3].toString()) {
                    safeSP.putAny("status_bar_layout_mode", 3)
                }
            })

        val layoutCompatibilityBinding = GetDataBinding({
            safeSP.getBoolean(
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
            safeSP.getInt(
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
            GetDataBinding({ safeSP.getInt("custom_clock_mode", 0) == 2 }

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
                customClockMode[safeSP.getInt(
                    "custom_clock_mode",
                    0
                )].toString()
            ) {
                add(customClockMode[0].toString()) {
                    safeSP.putAny("custom_clock_mode", 0)
                    customClockPresetBinding.binding.Send().send(false)
                    customClockGeekBinding.binding.Send().send(false)
                }
                add(customClockMode[1].toString()) {
                    safeSP.putAny("custom_clock_mode", 1)
                    customClockPresetBinding.binding.Send().send(true)
                    customClockGeekBinding.binding.Send().send(false)
                }
                add(customClockMode[2].toString()) {
                    safeSP.putAny("custom_clock_mode", 2)
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
                    safeSP.getString("custom_clock_format_geek", "HH:mm:ss"),
                    "",
                    isSingleLine = false
                )
                setLButton(textId = R.string.cancel) {
                    dismiss()
                }
                setRButton(textId = R.string.done) {
                    if (getEditText().isNotEmpty()) {
                        try {
                            safeSP.putAny("custom_clock_format_geek", getEditText())
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
            safeSP.getBoolean(
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
                align[safeSP.getInt(
                    "status_bar_network_speed_dual_row_gravity",
                    0
                )].toString()
            ) {
                add(align[0].toString()) {
                    safeSP.putAny("status_bar_network_speed_dual_row_gravity", 0)
                }
                add(align[1].toString()) {
                    safeSP.putAny("status_bar_network_speed_dual_row_gravity", 1)

                }
            },
            dataBindingRecv = statusBarDualRowNetworkSpeedBinding.binding.getRecv(2)
        )
        TextWithSpinner(
            TextV(textId = R.string.status_bar_network_speed_dual_row_icon),
            SpinnerV(
                safeSP.getString(
                    "status_bar_network_speed_dual_row_icon",
                    ""
                )
            ) {
                add("") {
                    safeSP.putAny(
                        "status_bar_network_speed_dual_row_icon",
                        ""
                    )
                }
                add("▲▼") {
                    safeSP.putAny("status_bar_network_speed_dual_row_icon", "▲▼")
                }
                add("△▽") {
                    safeSP.putAny("status_bar_network_speed_dual_row_icon", "△▽")
                }
                add("↑↓") {
                    safeSP.putAny("status_bar_network_speed_dual_row_icon", "↑↓")
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
        TitleText(textId = R.string.old_quick_settings_panel)
        val oldQSCustomSwitchBinding = GetDataBinding({
            safeSP.getBoolean(
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