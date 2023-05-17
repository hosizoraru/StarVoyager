package star.sky.voyager.activity.pages

import android.view.View
import android.widget.Switch
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
        TitleText(textId = R.string.status_bar_icon)
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.hide_icon,
                onClickListener = { showFragment("hide_icon") })
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
        Text(textId = R.string.maximum_number_of_notification_icons)
        SeekBarWithText("maximum_number_of_notification_icons", 1, 20, 3)
        Text(textId = R.string.maximum_number_of_notification_dots)
        SeekBarWithText("maximum_number_of_notification_dots", 0, 4, 3)
        Text(textId = R.string.maximum_number_of_lockscreen_notification_icons)
        SeekBarWithText("maximum_number_of_lockscreen_notification_icons", 1, 20, 3)
        Line()
        TitleText(textId = R.string.notification_center)
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
        Line()
        TitleText(textId = R.string.control_center)
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
    }
}