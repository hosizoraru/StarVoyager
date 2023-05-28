package star.sky.voyager.activity.pages.sub

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("lock_screen", "Lock Screen", hideMenu = false)
class LockScreenPage : BasePage() {
    override fun onCreate() {
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
                textId = R.string.lockscreen_charging_info,
                tipsId = R.string.only_one_choose
            ),
            SwitchV("lockscreen_charging_info", false)
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
    }
}