package star.sky.voyager.activity.pages.sub

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("game_turbo", "Game Turbo", hideMenu = false)
class GameTurboPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.screen_hold_on,
                tipsId = R.string.unlock_for_pad
            ),
            SwitchV("screen_hold_on", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.gun_service
            ),
            SwitchV("gun_service", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.macro_combo
            ),
            SwitchV("macro_combo", false)
        )
    }
}