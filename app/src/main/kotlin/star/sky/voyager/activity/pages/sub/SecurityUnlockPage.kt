package star.sky.voyager.activity.pages.sub

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("security_unlock", "Security Unlock", hideMenu = false)
class SecurityUnlockPage : BasePage() {
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
                textId = R.string.MEMC,
                tipsId = R.string.require_hardware_support
            ),
            SwitchV("MEMC", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.enhance_contours,
                tipsId = R.string.require_hardware_support
            ),
            SwitchV("enhance_contours", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.super_resolution,
                tipsId = R.string.require_hardware_support
            ),
            SwitchV("super_resolution", false)
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