package star.sky.voyager.activity.pages.apps

import android.annotation.SuppressLint
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@SuppressLint("NonConstantResourceId")
@BMPage("scope_gallery", titleId = R.string.scope_gallery, hideMenu = false)
//@BMPage("scope_gallery", "Gallery", hideMenu = false)
class GalleryPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.super_clipboard,
                onClickListener = { showFragment("super_clipboard") }
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.scope_screen_recorder,
                onClickListener = { showFragment("screen_recorder") }
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.scope_screen_shot,
                onClickListener = { showFragment("screen_shot") }
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.scope_media_editor,
                onClickListener = { showFragment("media_editor") }
            )
        )
        Line()
        TitleText(textId = R.string.scope_gallery)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.album_optimize,
                tipsId = R.string.album_optimize_summary
            ), SwitchV("album_optimize", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.open_video_self,
                tipsId = R.string.open_video_self_summary,
            ), SwitchV("open_video_self", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.hdr_enhance,
                tipsId = R.string.hdr_enhance_summary
            ), SwitchV("hdr_enhance", false)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.unlock,
                onClickListener = { showFragment("gallery_unlock") }
            )
        )
    }
}