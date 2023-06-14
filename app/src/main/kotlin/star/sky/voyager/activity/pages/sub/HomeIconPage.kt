package star.sky.voyager.activity.pages.sub

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("home_icon", "Home Icon", hideMenu = false)
class HomeIconPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.restore_google_app_icon
            ),
            SwitchV("restore_google_app_icon", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_scroll_icon_name,
                tipsId = R.string.home_scroll_icon_name_summary
            ),
            SwitchV("home_scroll_icon_name", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_unlock_cell_count,
            ), SwitchV("home_unlock_cell_count")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.unlock_hot_seat),
            SwitchV("unlock_hot_seat")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.icon_corner),
            SwitchV("icon_corner")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.shortcut_remove_restrictions),
            SwitchV("shortcut_remove_restrictions")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.add_freeform_shortcut
            ),
            SwitchV("add_freeform_shortcut", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.add_multi_instance_shortcut
            ),
            SwitchV("add_multi_instance_shortcut", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.freeform_multi_swap,
                tipsId = R.string.only_compatible_phone
            ),
            SwitchV("freeform_multi_swap", false)
        )
    }
}