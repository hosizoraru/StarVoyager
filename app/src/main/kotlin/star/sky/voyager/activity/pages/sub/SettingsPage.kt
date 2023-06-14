package star.sky.voyager.activity.pages.sub

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("scope_settings", "Settings", hideMenu = false)
class SettingsPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_settings)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.show_notification_importance,
                tipsId = R.string.show_notification_importance_summary
            ), SwitchV("show_notification_importance")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.speed_mode,
                tipsId = R.string.speed_mode_summary
            ), SwitchV("speed_mode")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_settings_permission_unknown_origin_app,
                tipsId = R.string.system_settings_permission_unknown_origin_app_desc
            ), SwitchV("system_settings_permission_unknown_origin_app")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.no_through_the_list,
            ), SwitchV("no_through_the_list")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.new_nfc_page,
                tipsId = R.string.new_nfc_page_summary
            ), SwitchV("new_nfc_page")
        )
    }
}