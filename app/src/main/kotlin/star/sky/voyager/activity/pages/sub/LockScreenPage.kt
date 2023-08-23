package star.sky.voyager.activity.pages.sub

import android.annotation.SuppressLint
import android.view.View
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SeekBarWithTextV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextV
import star.sky.voyager.R

@SuppressLint("NonConstantResourceId")
@BMPage("lock_screen", titleId = R.string.lock_screen, hideMenu = false)
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
            TextSummaryV(textId = R.string.lock_screen_zen_mode),
            SwitchV("lock_screen_zen_mode")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.lock_screen_hint),
            SwitchV("lock_screen_hint")
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
        val chargingInfo = GetDataBinding({
            safeSP.getBoolean(
                "lockscreen_charging_info",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.lockscreen_charging_info,
                tipsId = R.string.only_one_choose
            ),
            SwitchV("lockscreen_charging_info", false, dataBindingSend = chargingInfo.bindingSend)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.current_mA
            ), SwitchV("current_mA", false), dataBindingRecv = chargingInfo.binding.getRecv(1)
        )
        TextWithSeekBar(
            TextV(textId = R.string.lockscreen_charging_info_refresh_frequency),
            SeekBarWithTextV("lockscreen_charging_info_refresh_frequency", 1, 35, 10),
            dataBindingRecv = chargingInfo.binding.getRecv(1)
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
                textId = R.string.lock_screen_only_left,
                tipsId = R.string.lock_screen_only_left_summary,
            ), SwitchV("lock_screen_only_left")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.remove_lock_screen_camera,
                tipsId = R.string.only_official_default_themes_are_supported
            ), SwitchV("remove_lock_screen_camera")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.lock_screen_uwb),
            SwitchV("lock_screen_uwb")
        )
        val flashLightInfo = GetDataBinding({
            safeSP.getBoolean(
                "lock_screen_left_button_flash_light",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.lock_screen_left_button_flash_light
            ),
            SwitchV(
                "lock_screen_left_button_flash_light",
                false,
                dataBindingSend = flashLightInfo.bindingSend
            )
        )
        Text(
            textId = R.string.trigger_condition,
            dataBindingRecv = flashLightInfo.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.click
            ),
            SwitchV("flash_light_click"),
            dataBindingRecv = flashLightInfo.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.long_click
            ),
            SwitchV("flash_light_long_click"),
            dataBindingRecv = flashLightInfo.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.double_click
            ),
            SwitchV("flash_light_double_click"),
            dataBindingRecv = flashLightInfo.binding.getRecv(1)
        )
    }
}