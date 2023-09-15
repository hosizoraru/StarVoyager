package star.sky.voyager.activity.pages.apps

import android.annotation.SuppressLint
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@SuppressLint("NonConstantResourceId")
@BMPage("scope_settings", titleId = R.string.scope_settings, hideMenu = false)
//@BMPage("scope_settings", "Settings", hideMenu = false)
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
                textId = R.string.show_notification_history_and_log,
            ), SwitchV("show_notification_history_and_log")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.google_settings,
            ), SwitchV("google_settings")
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
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.unlock,
                onClickListener = { showFragment("settings_unlock") }
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.mod,
                onClickListener = { showFragment("settings_mod") }
            )
        )
    }
}