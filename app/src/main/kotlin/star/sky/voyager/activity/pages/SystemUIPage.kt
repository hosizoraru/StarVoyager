package star.sky.voyager.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextV
import star.sky.voyager.R

@BMPage("scope_system_ui", "System UI", hideMenu = false)
class SystemUIPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.status_bar)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_ui_show_status_bar_battery,
                tipsId = R.string.system_ui_show_status_bar_battery_summary
            ),
            SwitchV("system_ui_show_status_bar_battery", false)
        )
        Line()
        TitleText(textId = R.string.status_bar_icon)
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
            TextSummaryV(textId = R.string.enable_wave_charge_animation),
            SwitchV("enable_wave_charge_animation")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.lock_screen_charging_current,
                tipsId = R.string.only_official_default_themes_are_supported
            ), SwitchV("lock_screen_charging_current")
        )
    }
}