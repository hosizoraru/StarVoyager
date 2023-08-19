package star.sky.voyager.activity.pages.apps

import android.annotation.SuppressLint
import android.widget.Toast
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R
import star.sky.voyager.utils.yife.Terminal.exec

@SuppressLint("NonConstantResourceId")
@BMPage("scope_security_center", titleId = R.string.scope_security_center, hideMenu = false)
class SecurityPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_security_center)
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.unlock,
                onClickListener = { showFragment("security_unlock") }
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.game_turbo,
                onClickListener = { showFragment("game_turbo") }
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.front_camera_assistant,
                onClickListener = { showFragment("front_camera_assistant") }
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.scope_barrage,
                onClickListener = { showFragment("barrage") }
            )
        )
        Line()
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.skip_waiting_time,
                tipsId = R.string.skip_waiting_time_summary
            ), SwitchV("skip_waiting_time")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.remove_open_app_confirmation_popup,
                tipsId = R.string.remove_open_app_confirmation_popup_summary
            ), SwitchV("remove_open_app_confirmation_popup")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.lock_one_hundred,
                tipsId = R.string.lock_one_hundred_summary
            ), SwitchV("lock_one_hundred")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.open_by_default_setting
            ),
            SwitchV("open_by_default_setting", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.remove_report
            ),
            SwitchV("remove_report", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.remove_risk_pkg
            ),
            SwitchV("remove_risk_pkg", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.disable_app_settings
            ),
            SwitchV("disable_app_settings", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.remove_conversation_bubble_settings_restriction,
                tipsId = R.string.remove_conversation_bubble_settings_restriction_summary
            ), SwitchV("remove_conversation_bubble_settings_restriction")
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.try_to_fix_conversation_bubbles,
                tipsId = R.string.try_to_fix_conversation_bubbles_summary
            ) {
                exec("pm enable com.miui.securitycenter/com.miui.bubbles.services.BubblesNotificationListenerService")
                Toast.makeText(
                    activity,
                    getString(R.string.finished),
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }
}