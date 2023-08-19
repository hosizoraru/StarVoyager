package star.sky.voyager.activity.pages.apps

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R

@BMPage("screen_shot", "Screen Shot", hideMenu = false)
class ScreenShotPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_screen_shot)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.save_to_pictures
            ),
            SwitchV("save_to_pictures", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.save_as_png
            ),
            SwitchV("save_as_png", false)
        )
        val deviceBinding = GetDataBinding({
            safeSP.getBoolean(
                "device_shell",
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
                "device_shell",
                false,
                dataBindingSend = deviceBinding.bindingSend
            ),
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.your_product_market_name,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.device_shell)
                        setMessage(
                            "${activity.getString(R.string.def)}ishtar\n${
                                activity.getString(
                                    R.string.current
                                )
                            }${
                                safeSP.getString(
                                    "device_shell_s",
                                    "ishtar"
                                )
                            }"
                        )
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "device_shell_s",
                                    "ishtar"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "device_shell_s",
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