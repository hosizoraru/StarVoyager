package star.sky.voyager.activity.pages.sub

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("super_clipboard", "Super Clipboard", hideMenu = false)
class SuperClipboardPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_gallery)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.super_clipboard,
                tipsId = R.string.super_clipboard_summary
            ), SwitchV("gallery_super_clipboard", false)
        )
        TitleText(textId = R.string.scope_file_explorer)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.super_clipboard,
            ), SwitchV("file_explorer_super_clipboard", false)
        )
        TitleText(textId = R.string.scope_screen_shot)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.super_clipboard,
            ), SwitchV("screen_shot_super_clipboard", false)
        )
        TitleText(textId = R.string.scope_browser)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.super_clipboard,
            ), SwitchV("browser_super_clipboard", false)
        )
        TitleText(textId = R.string.scope_ta_plus)
    }
}