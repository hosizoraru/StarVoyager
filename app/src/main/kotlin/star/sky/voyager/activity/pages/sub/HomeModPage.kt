package star.sky.voyager.activity.pages.sub

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SeekBarWithTextV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextV
import star.sky.voyager.R

//@SuppressLint("NonConstantResourceId")
//@BMPage("home_mod", titleId = R.string.mod, hideMenu = false)
@BMPage("home_mod", "Home Mod", hideMenu = false)
class HomeModPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithSeekBar(
            TextSummaryV(
                textId = R.string.icon_label_size
            ), SeekBarWithTextV("icon_label_size", 0, 30, 12)
        )
        val foldDockBinding = GetDataBinding({
            safeSP.getBoolean(
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
            SeekBarWithTextV("fold_dock_hot_seating", 1, 10, 3),
            dataBindingRecv = foldDockBinding.getRecv(1)
        )
        TextWithSeekBar(
            TextV(textId = R.string.fold_dock_run),
            SeekBarWithTextV("fold_dock_running", 1, 10, 2),
            dataBindingRecv = foldDockBinding.getRecv(1)
        )

        val animRatioBinding = GetDataBinding({
            safeSP.getBoolean(
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
            safeSP.getBoolean(
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
            safeSP.getBoolean(
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

        val backAreaHeightBinding = GetDataBinding({
            safeSP.getBoolean(
                "back_gesture_area_height",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.back_gesture_area_height),
            SwitchV("back_gesture_area_height", dataBindingSend = backAreaHeightBinding.bindingSend)
        )
        TextWithSeekBar(
            TextV(textId = R.string.wmlp_height),
            SeekBarWithTextV("wmlp_height", 10, 100, 60),
            dataBindingRecv = backAreaHeightBinding.getRecv(1)
        )
    }
}