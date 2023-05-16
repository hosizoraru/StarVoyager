package star.sky.voyager.activity.pages

import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("scope_gallery", "Gallery", hideMenu = false)

class GalleryPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_gallery)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.Unlock_HDR_Enhance,
                tipsId = R.string.Unlock_HDR_Enhance_summary
            ), SwitchV("Unlock_HDR_Enhance", false)
        )
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
        Line()
        TitleText(textId = R.string.scope_media_editor)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.filter_manager,
            ), SwitchV("filter_manager")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.unlock_unlimited_cropping,
                tipsId = R.string.unlock_unlimited_cropping_summary
            ), SwitchV("unlock_unlimited_cropping")
        )
        Line()
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
    }
}