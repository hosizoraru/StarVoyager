package star.sky.voyager.activity.pages.apps

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("screen_recorder", "Screen Recorder", hideMenu = false)
class ScreenRecorderPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_screen_recorder)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.force_enable_native_playback_capture
            ),
            SwitchV("force_enable_native_playback_capture", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.change_bitrate_and_frame_rate_range
            ),
            SwitchV("modify_screen_recorder_config", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.save_to_movies
            ),
            SwitchV("save_to_movies", false)
        )
    }
}