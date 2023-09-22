package star.sky.voyager.activity.pages.apps

import android.annotation.SuppressLint
import android.view.View
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SeekBarWithTextV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextV
import star.sky.voyager.R

@SuppressLint("NonConstantResourceId")
@BMPage("scope_personal_assistant", titleId = R.string.scope_personal_assistant, hideMenu = false)
//@BMPage("scope_personal_assistant", "Personal Assistant", hideMenu = false)
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
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.Use_Transition_Animation),
            SwitchV("Use_Transition_Animation")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.Show_MIUI_Widget),
            SwitchV("Show_MIUI_Widget")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.resizable_widgets),
            SwitchV("resizable_widgets")
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
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.assistant_location_cn,
                tipsId = R.string.open_on_demand,
            ),
            SwitchV("assistant_location_cn")
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
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.miui_method,
            ),
            SwitchV(
                "blur_personal_assistant_xiaomi_method",
                false,
            ), dataBindingRecv = blurPersonalAssistantBinding.binding.getRecv(1)
        )
        TextWithSeekBar(
            TextV(textId = R.string.blur_personal_assistant_radius),
            SeekBarWithTextV("blur_personal_assistant_radius", 30, 99, 80),
            dataBindingRecv = blurPersonalAssistantBinding.binding.getRecv(1)
        )
    }
}