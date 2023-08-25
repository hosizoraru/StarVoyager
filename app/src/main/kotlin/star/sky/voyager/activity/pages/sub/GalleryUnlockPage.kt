package star.sky.voyager.activity.pages.sub

import android.annotation.SuppressLint
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@SuppressLint("NonConstantResourceId")
@BMPage("gallery_unlock", titleId = R.string.unlock, hideMenu = false)
//@BMPage("gallery_unlock", "Gallery Unlock", hideMenu = false)
class GalleryUnlockPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.pdf,
            ), SwitchV("pdf")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.ocr,
            ), SwitchV("ocr")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.ocr_form,
            ), SwitchV("ocr_form")
        )
        Line()
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.id_photo,
            ), SwitchV("id_photo")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.photo_movie,
            ), SwitchV("photo_movie")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.video_post,
            ), SwitchV("video_post")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.video_editor,
            ), SwitchV("video_editor")
        )
        Line()
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.text_yan_hua,
            ), SwitchV("text_yan_hua")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.remover_2,
            ), SwitchV("remover_2")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.magic_matting,
            ), SwitchV("magic_matting")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.magic_sky,
            ), SwitchV("magic_sky")
        )
    }
}