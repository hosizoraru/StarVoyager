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
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.game_box_vision_enhance,
                tipsId = R.string.require_hardware_support,
            ),
            SwitchV("game_box_vision_enhance", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.dynamic_performance,
            ),
            SwitchV("dynamic_performance", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.audio_optimization,
            ),
            SwitchV("audio_optimization", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.wifi_low_latency_mode,
            ),
            SwitchV("wifi_low_latency_mode", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.touchfeature_gamemode,
            ),
            SwitchV("touchfeature_gamemode", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.ai_subtitles_videomode,
            ),
            SwitchV("ai_subtitles_videomode", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.fps_switch,
            ),
            SwitchV("fps_switch", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.enhanced_dsda,
            ),
            SwitchV("enhanced_dsda", false)
        )
    }
}