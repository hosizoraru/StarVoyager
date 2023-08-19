package star.sky.voyager.activity.pages.apps

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R

@BMPage("scope_gallery", "Gallery", hideMenu = false)

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
        val deviceBinding = GetDataBinding({
            safeSP.getBoolean(
                "device_shell2",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.device_shell,
            ),
            SwitchV(
                "device_shell2",
                false,
                dataBindingSend = deviceBinding.bindingSend
            ),
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.Market_As,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.Market_As)
                        setMessage(
                            "${activity.getString(R.string.def)}raphael\n${
                                activity.getString(
                                    R.string.current
                                )
                            }${
                                safeSP.getString(
                                    "device_shell_s2",
                                    "raphael"
                                )
                            }"
                        )
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "device_shell_s2",
                                    "raphael"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "device_shell_s2",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = deviceBinding.binding.getRecv(1)
        )
    }
}