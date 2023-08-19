package star.sky.voyager.activity.pages.apps

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R

@BMPage("media_editor", "Media Editor", hideMenu = false)
class MediaEditorPage : BasePage() {
    override fun onCreate() {
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