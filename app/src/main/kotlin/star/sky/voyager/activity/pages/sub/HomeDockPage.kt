package star.sky.voyager.activity.pages.sub

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R

@BMPage("home_dock", "Home Dock", hideMenu = false)
class HomeDockPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.restore_google_search
            ),
            SwitchV("restore_google_search", false)
        )
        val xiaoAiBinding = GetDataBinding({
            safeSP.getBoolean(
                "search_bar_xiaoai_custom",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.search_bar_xiaoai_custom),
            SwitchV("search_bar_xiaoai_custom", dataBindingSend = xiaoAiBinding.bindingSend)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.search_bar_pkg_name,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.search_bar_pkg_name)
                        setMessage(
                            "${activity.getString(R.string.def)}com.google.android.googlequicksearchbox\n${
                                activity.getString(
                                    R.string.current
                                )
                            }${
                                safeSP.getString(
                                    "search_bar_pkg_name",
                                    "com.google.android.googlequicksearchbox"
                                )
                            }"
                        )
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "search_bar_pkg_name",
                                    "com.google.android.googlequicksearchbox"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "search_bar_pkg_name",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = xiaoAiBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.search_bar_cls_name,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.search_bar_cls_name)
                        setMessage(
                            "${activity.getString(R.string.def)}com.google.android.apps.searchlite.SearchActivity\n${
                                activity.getString(
                                    R.string.current
                                )
                            }${
                                safeSP.getString(
                                    "search_bar_cls_name",
                                    "com.google.android.apps.searchlite.SearchActivity"
                                )
                            }"
                        )
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "search_bar_cls_name",
                                    "com.google.android.apps.searchlite.SearchActivity"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "search_bar_cls_name",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = xiaoAiBinding.binding.getRecv(1)
        )
    }
}