package star.sky.voyager.activity.pages.sub

import android.annotation.SuppressLint
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@SuppressLint("NonConstantResourceId")
@BMPage("security_unlock", titleId = R.string.unlock, hideMenu = false)
class SecurityUnlockPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_security_center)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.screen_time,
                tipsId = R.string.screen_time_summary
            ),
            SwitchV("screen_time", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.battery_life_function
            ),
            SwitchV("battery_life_function")
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
    }
}