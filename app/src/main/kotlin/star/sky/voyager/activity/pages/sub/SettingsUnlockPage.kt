package star.sky.voyager.activity.pages.sub

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("settings_unlock", "Settings Unlock", hideMenu = false)
class SettingsUnlockPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.new_nfc_page,
                tipsId = R.string.new_nfc_page_summary
            ), SwitchV("new_nfc_page")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.novelty_haptic,
                tipsId = R.string.novelty_haptic_summary
            ), SwitchV("novelty_haptic")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.speed_mode,
                tipsId = R.string.speed_mode_summary
            ), SwitchV("speed_mode")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.unlock_taplus
            ), SwitchV("unlock_taplus")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.show_vip_service
            ), SwitchV("show_vip_service")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.battery_style
            ), SwitchV("battery_style")
        )
    }
}