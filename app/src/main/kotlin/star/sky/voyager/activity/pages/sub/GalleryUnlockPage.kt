package star.sky.voyager.activity.pages.sub

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("gallery_unlock", "Gallery Unlock", hideMenu = false)
class GalleryUnlockPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.enable_pdf,
            ), SwitchV("enable_pdf")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.enable_ocr,
            ), SwitchV("enable_ocr")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.enable_ocr_form,
            ), SwitchV("enable_ocr_form")
        )
        Line()
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.enable_id_photo,
            ), SwitchV("enable_id_photo")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.enable_photo_movie,
            ), SwitchV("enable_photo_movie")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.enable_video_post,
            ), SwitchV("enable_video_post")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.enable_video_editor,
            ), SwitchV("enable_video_editor")
        )
        Line()
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.enable_text_yan_hua,
            ), SwitchV("enable_text_yan_hua")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.enable_remover_2,
            ), SwitchV("enable_remover_2")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.enable_magic_matting,
            ), SwitchV("enable_magic_matting")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.enable_magic_sky,
            ), SwitchV("enable_magic_sky")
        )
    }
}