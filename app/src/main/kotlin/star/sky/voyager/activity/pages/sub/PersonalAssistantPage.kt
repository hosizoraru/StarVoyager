package star.sky.voyager.activity.pages.sub

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SeekBarWithTextV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextV
import star.sky.voyager.R


@BMPage("scope_personal_assistant", "Personal Assistant", hideMenu = false)
class PersonalAssistantPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_miui_home)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.Overlap_Mode),
            SwitchV("Overlap_Mode")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.restore_switch_minus_screen
            ),
            SwitchV("restore_switch_minus_screen", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.home_widget_to_minus),
            SwitchV("home_widget_to_minus")
        )
        Line()
        TitleText(textId = R.string.scope_personal_assistant)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.exposure_refresh_for_non_miui_widget,
                tipsId = R.string.exposure_refresh_for_non_miui_widget_summary
            ),
            SwitchV("exposure_refresh_for_non_miui_widget", false)
        )
        val blurPersonalAssistantBinding = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
                "blur_personal_assistant",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.blur_personal_assistant,
            ),
            SwitchV(
                "blur_personal_assistant",
                false,
                dataBindingSend = blurPersonalAssistantBinding.bindingSend
            )
        )
        TextWithSeekBar(
            TextV(textId = R.string.blur_personal_assistant_radius),
            SeekBarWithTextV("blur_personal_assistant_radius", 30, 99, 80),
            dataBindingRecv = blurPersonalAssistantBinding.binding.getRecv(1)
        )
    }
}