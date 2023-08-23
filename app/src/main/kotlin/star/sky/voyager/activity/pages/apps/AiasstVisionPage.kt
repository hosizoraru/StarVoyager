package star.sky.voyager.activity.pages.apps

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

//@SuppressLint("NonConstantResourceId")
//@BMPage("aiasst_vision", titleId = R.string.scope_aiasst_vision, hideMenu = false)
@BMPage("aiasst_vision", "Aiasst Vision", hideMenu = false)
class AiasstVisionPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_aiasst_vision)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.meeting_mode,
            ), SwitchV("meeting_mode")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.desktop_mode_screen_translate,
            ), SwitchV("desktop_mode_screen_translate")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.split_screen_translate,
                tipsId = R.string.split_screen_translate_summary,
            ), SwitchV("split_screen_translate")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.ai_subtitles,
            ), SwitchV("ai_subtitles")
        )
    }
}