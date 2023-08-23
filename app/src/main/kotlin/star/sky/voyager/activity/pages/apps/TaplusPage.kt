package star.sky.voyager.activity.pages.apps

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R

//@SuppressLint("NonConstantResourceId")
//@BMPage("taplus", titleId = R.string.scope_ta_plus, hideMenu = false)
@BMPage("taplus", "Taplus", hideMenu = false)
class TaplusPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_ta_plus)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.horizontal_content_extension),
            SwitchV("horizontal_content_extension")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.unlock_taplus_for_pad,
                tipsId = R.string.open_on_demand
            ),
            SwitchV("unlock_taplus_for_pad")
        )
        val browserBinding = GetDataBinding({
            safeSP.getBoolean(
                "taplus_browser",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.taplus_browser
            ), SwitchV(
                "taplus_browser",
                false,
                dataBindingSend = browserBinding.bindingSend
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.taplus_browser_pkg,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.taplus_browser_pkg)
                        setMessage(
                            "${activity.getString(R.string.def)}com.android.browser\n${
                                activity.getString(
                                    R.string.current
                                )
                            }${
                                safeSP.getString(
                                    "taplus_browser_pkg",
                                    "com.android.browser"
                                )
                            }"
                        )
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "taplus_browser_pkg",
                                    "com.android.browser"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "taplus_browser_pkg",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = browserBinding.binding.getRecv(1)
        )
    }
}