package star.sky.voyager.activity.pages.sub

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R

@BMPage("home_icon", "Home Icon", hideMenu = false)
class HomeIconPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.restore_google_app_icon
            ),
            SwitchV("restore_google_app_icon", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_scroll_icon_name,
                tipsId = R.string.home_scroll_icon_name_summary
            ),
            SwitchV("home_scroll_icon_name", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_unlock_cell_count,
            ), SwitchV("home_unlock_cell_count")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.unlock_hot_seat),
            SwitchV("unlock_hot_seat")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.icon_corner),
            SwitchV("icon_corner")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.shortcut_remove_restrictions),
            SwitchV("shortcut_remove_restrictions")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.fake_non_default_icon),
            SwitchV("fake_non_default_icon")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.enable_perfect_icons),
            SwitchV("enable_perfect_icons")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.add_freeform_shortcut
            ),
            SwitchV("add_freeform_shortcut", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.add_multi_instance_shortcut
            ),
            SwitchV("add_multi_instance_shortcut", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.freeform_multi_swap,
                tipsId = R.string.only_compatible_phone
            ),
            SwitchV("freeform_multi_swap", false)
        )
        val shortcutTitleBinding = GetDataBinding({
            safeSP.getBoolean(
                "shortcut_title_custom",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.shortcut_title_custom
            ),
            SwitchV(
                "shortcut_title_custom",
                false,
                dataBindingSend = shortcutTitleBinding.bindingSend
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.app_info,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.app_info)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString("app_info", "App Info")
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "app_info",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = shortcutTitleBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.share,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.share)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString("share", "Share")
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "share",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = shortcutTitleBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.remove,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.remove)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString("remove", "Remove")
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "remove",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = shortcutTitleBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.uninstall,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.uninstall)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString("uninstall", "Uninstall")
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "uninstall",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = shortcutTitleBinding.binding.getRecv(1)
        )
    }
}