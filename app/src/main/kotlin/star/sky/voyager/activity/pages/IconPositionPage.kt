package star.sky.voyager.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextV
import star.sky.voyager.R

@BMPage("icon_position", "Icon Position", hideMenu = false)
class IconPositionPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.at_right_status_bar_icon)
        TextWithSwitch(
            TextV(textId = R.string.at_right_alarm_icon),
            SwitchV("at_right_alarm_icon")
        )
        TextWithSwitch(
            TextV(textId = R.string.at_right_nfc_icon),
            SwitchV("at_right_nfc_icon")
        )
        TextWithSwitch(
            TextV(textId = R.string.at_right_volume_icon),
            SwitchV("at_right_volume_icon")
        )
        TextWithSwitch(
            TextV(textId = R.string.at_right_zen_icon),
            SwitchV("at_right_zen_icon")
        )
    }
}