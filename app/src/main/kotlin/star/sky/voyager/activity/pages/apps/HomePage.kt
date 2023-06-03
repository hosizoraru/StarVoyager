package star.sky.voyager.activity.pages.apps

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SeekBarWithTextV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R

@BMPage("scope_miui_home", "Home", hideMenu = false)
class HomePage : BasePage() {
    override fun onCreate() {
        Page(
            activity.getDrawable(R.drawable.ic_appvault)!!,
            pageNameId = R.string.scope_personal_assistant, round = 8f,
            onClickListener = { showFragment("scope_personal_assistant") }
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.blur,
                onClickListener = { showFragment("home_blur") }
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.mod,
                onClickListener = { showFragment("home_mod") }
            )
        )
        Line()
        TitleText(textId = R.string.scope_miui_home)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_time,
                tipsId = R.string.home_time_summary
            ), SwitchV("home_time")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.double_tap_to_sleep,
                tipsId = R.string.double_tap_to_sleep_summary
            ), SwitchV("double_tap_to_sleep")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_recent_view_wallpaper_darkening,
                tipsId = R.string.home_recent_view_wallpaper_darkening_summary
            ), SwitchV("home_recent_view_wallpaper_darkening", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_recent_view_remove_card_animation,
                tipsId = R.string.home_recent_view_remove_card_animation_summary
            ), SwitchV("home_recent_view_remove_card_animation", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.add_freeform_shortcut
            ),
            SwitchV("add_freeform_shortcut", false)
        )
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
                textId = R.string.home_real_memory,
                tipsId = R.string.home_real_memory_summary
            ),
            SwitchV("home_real_memory", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.Use_Transition_Animation),
            SwitchV("Use_Transition_Animation")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.Show_MIUI_Widget),
            SwitchV("Show_MIUI_Widget")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.resizable_widgets),
            SwitchV("resizable_widgets")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.shortcut_remove_restrictions),
            SwitchV("shortcut_remove_restrictions")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.unlock_hot_seat),
            SwitchV("unlock_hot_seat")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.optimize_unlock_anim),
            SwitchV("optimize_unlock_anim")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.icon_corner),
            SwitchV("icon_corner")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_unlock_cell_count,
            ), SwitchV("home_unlock_cell_count")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.home_folder_width),
            SwitchV("home_folder_width")
        )
        Line()
        TextWithSeekBar(
            TextV(textId = R.string.home_folder_columns),
            SeekBarWithTextV("home_folder_columns", 2, 7, 3)
        )
        val monoBinding = GetDataBinding({
            safeSP.getBoolean(
                "mono_chrome_icon",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.mono_chrome_icon,
            ), SwitchV("mono_chrome_icon", false, dataBindingSend = monoBinding.bindingSend)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.monet_color,
            ), SwitchV("monet_color", false), dataBindingRecv = monoBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.use_edit_color,
            ), SwitchV("use_edit_color", false), dataBindingRecv = monoBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.your_color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.your_color)
                        setMessage(
                            "${activity.getString(R.string.def)}#0d84ff\n${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "your_color",
                                    "#0d84ff"
                                )
                            }"
                        )
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString("your_color", "#0d84ff")
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "your_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = monoBinding.binding.getRecv(1)
        )
    }
}