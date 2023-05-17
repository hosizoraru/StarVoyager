package star.sky.voyager.activity.pages

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity
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
            TextSummaryV(textId = R.string.Overlap_Mode),
            SwitchV("Overlap_Mode")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.home_widget_to_minus),
            SwitchV("home_widget_to_minus")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.Show_MIUI_Widget),
            SwitchV("Show_MIUI_Widget")
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
            TextSummaryV(
                textId = R.string.home_blur_when_open_folder,
                tipsId = R.string.home_blur_when_open_folder_summary
            ), SwitchV("home_blur_when_open_folder")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.home_blur_wallpaper),
            SwitchV("home_blur_wallpaper")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.home_folder_width),
            SwitchV("home_folder_width")
        )
        val blurBindingBlur = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
                "home_use_complete_blur",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_use_complete_blur,
                tipsId = R.string.home_use_complete_blur_summary
            ),
            SwitchV("home_use_complete_blur", false, dataBindingSend = blurBindingBlur.bindingSend)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_complete_blur_fix,
                tipsId = R.string.home_complete_blur_fix_summary
            ),
            SwitchV("home_complete_blur_fix", false),
            dataBindingRecv = blurBindingBlur.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_high_end_device,
                tipsId = R.string.home_high_end_device_summary
            ),
            SwitchV("home_high_end_device", false)
        )
        Line()
        TextWithSeekBar(
            TextV(textId = R.string.home_folder_columns),
            SeekBarWithTextV("home_folder_columns", 2, 7, 3)
        )
        val foldDockBinding = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
                "home_fold_dock",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.home_fold_dock),
            SwitchV("home_fold_dock", dataBindingSend = foldDockBinding.bindingSend)
        )
        TextWithSeekBar(
            TextV(textId = R.string.fold_dock_hot_seat),
            SeekBarWithTextV("fold_dock_hot_seat", 1, 10, 3),
            dataBindingRecv = foldDockBinding.getRecv(1)
        )
        TextWithSeekBar(
            TextV(textId = R.string.fold_dock_run),
            SeekBarWithTextV("fold_dock_run", 1, 10, 2),
            dataBindingRecv = foldDockBinding.getRecv(1)
        )

        val animRatioBinding = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
                "home_anim_ratio_binding",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_anim_ratio_binding,
                tipsId = R.string.home_anim_ratio_binding_summary
            ),
            SwitchV("home_anim_ratio_binding", dataBindingSend = animRatioBinding.bindingSend)
        )
        TextSummaryWithSeekBar(
            TextSummaryV(
                textId = R.string.home_anim_ratio,
                tipsId = R.string.home_anim_ratio_summary
            ),
            SeekBarWithTextV("home_anim_ratio", 0, 300, 100),
            dataBindingRecv = animRatioBinding.getRecv(1)
        )
        TextSummaryWithSeekBar(
            TextSummaryV(
                textId = R.string.home_anim_ratio_recent,
                tipsId = R.string.home_anim_ratio_recent_summary
            ),
            SeekBarWithTextV("home_anim_ratio_recent", 0, 300, 100),
            dataBindingRecv = animRatioBinding.getRecv(1)
        )

        val cardSizeBinding = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
                "home_task_view_card_size_binding",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_task_view_card_size_binding,
                tipsId = R.string.home_task_view_card_size_binding_summary
            ),
            SwitchV(
                "home_task_view_card_size_binding",
                dataBindingSend = cardSizeBinding.bindingSend
            )
        )
        TextWithSeekBar(
            TextV(textId = R.string.home_task_view_card_size_vertical),
            SeekBarWithTextV("home_task_view_card_size_vertical", 80, 120, 100),
            dataBindingRecv = cardSizeBinding.getRecv(1)
        )
        TextWithSeekBar(
            TextV(textId = R.string.home_task_view_card_size_horizontal1),
            SeekBarWithTextV("home_task_view_card_size_horizontal1", 80, 120, 100),
            dataBindingRecv = cardSizeBinding.getRecv(1)
        )
        TextWithSeekBar(
            TextV(textId = R.string.home_task_view_card_size_horizontal2),
            SeekBarWithTextV("home_task_view_card_size_horizontal2", 80, 120, 100),
            dataBindingRecv = cardSizeBinding.getRecv(1)
        )

        val homeFoldAnimBinding = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
                "home_folder_anim",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.home_folder_anim),
            SwitchV("home_folder_anim", dataBindingSend = homeFoldAnimBinding.bindingSend)
        )
        TextWithSeekBar(
            TextV(textId = R.string.home_folder_anim_1),
            SeekBarWithTextV("home_folder_anim_1", 50, 150, 90),
            dataBindingRecv = homeFoldAnimBinding.getRecv(1)
        )
        TextWithSeekBar(
            TextV(textId = R.string.home_folder_anim_2),
            SeekBarWithTextV("home_folder_anim_2", 10, 60, 30),
            dataBindingRecv = homeFoldAnimBinding.getRecv(1)
        )
        TextWithSeekBar(
            TextV(textId = R.string.home_folder_anim_3),
            SeekBarWithTextV("home_folder_anim_3", 50, 150, 99),
            dataBindingRecv = homeFoldAnimBinding.getRecv(1)
        )
        TextWithSeekBar(
            TextV(textId = R.string.home_folder_anim_4),
            SeekBarWithTextV("home_folder_anim_4", 10, 60, 24),
            dataBindingRecv = homeFoldAnimBinding.getRecv(1)
        )
        val monoBinding = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
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
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                MIUIActivity.safeSP.getString("your_color", "#0d84ff")
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                MIUIActivity.safeSP.putAny(
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