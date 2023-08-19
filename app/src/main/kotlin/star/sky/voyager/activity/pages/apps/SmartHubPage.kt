package star.sky.voyager.activity.pages.apps

import android.annotation.SuppressLint
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@SuppressLint("NonConstantResourceId")
@BMPage("scope_mi_smart_hub", titleId = R.string.scope_mi_smart_hub, hideMenu = false)
class SmartHubPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_mi_share)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.No_Auto_Turn_Off,
                tipsId = R.string.No_Auto_Turn_Off_summary
            ), SwitchV("No_Auto_Turn_Off")
        )
        Line()
        TitleText(textId = R.string.scope_cast)
        TitleText(textId = R.string.scope_miui_plus)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.force_support_send_app,
            ), SwitchV("force_support_send_app")
        )
    }
}