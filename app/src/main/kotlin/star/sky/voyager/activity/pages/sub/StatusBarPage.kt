package star.sky.voyager.activity.pages.sub

import android.annotation.SuppressLint
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
import star.sky.voyager.utils.yife.Build.IS_TABLET

@SuppressLint("NonConstantResourceId")
@BMPage("status_bar", titleId = R.string.status_bar, hideMenu = false)
class StatusBarPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.status_bar)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.double_tap_to_sleep),
            SwitchV("status_bar_double_tap_to_sleep")
        )
        val batteryBinding = GetDataBinding({
            safeSP.getBoolean(
                "system_ui_show_status_bar_battery",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_ui_show_status_bar_battery,
                tipsId = R.string.system_ui_show_status_bar_battery_summary
            ),
            SwitchV(
                "system_ui_show_status_bar_battery",
                false,
                dataBindingSend = batteryBinding.bindingSend
            )
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.show_status_bar_battery_any
            ),
            SwitchV("show_status_bar_battery_any", false),
            dataBindingRecv = batteryBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.current_mA
            ), SwitchV("current_mA", false),
            dataBindingRecv = batteryBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.hide_status_bar_when_screenshot),
            SwitchV("hide_status_bar_when_screenshot")
        )
        Line()
        val colorBinding = GetDataBinding({
            safeSP.getBoolean(
                "status_bar_battery_text_color_custom_enable",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.status_bar_battery_text_color_custom_enable,
                tipsId = R.string.status_bar_battery_text_color_custom_enable_summary
            ),
            SwitchV(
                "status_bar_battery_text_color_custom_enable",
                false,
                dataBindingSend = colorBinding.bindingSend
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.status_bar_battery_text_color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.status_bar_battery_text_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString("status_bar_battery_text_color", "#0d84ff")
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "status_bar_battery_text_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = colorBinding.binding.getRecv(1)
        )
        Line()
        TextWithSeekBar(
            TextV(textId = R.string.status_bar_battery_text_size),
            SeekBarWithTextV("status_bar_battery_text_size", 1, 20, 8)
        )

        TextWithSeekBar(
            TextV(textId = R.string.status_bar_battery_line_spacing_add),
            SeekBarWithTextV("status_bar_battery_line_spacing_add", 0, 20, 0)
        )

        TextWithSeekBar(
            TextV(textId = R.string.status_bar_battery_line_spacing_multi),
            SeekBarWithTextV("status_bar_battery_line_spacing_multi", 20, 120, 80)
        )
        Line()
        TextWithSeekBar(
            TextV(textId = R.string.status_bar_battery_left_padding),
            SeekBarWithTextV("status_bar_battery_left_padding", -1, 20, if (IS_TABLET) 0 else 8)
        )

        TextWithSeekBar(
            TextV(textId = R.string.status_bar_battery_right_padding),
            SeekBarWithTextV("status_bar_battery_right_padding", -1, 20, if (IS_TABLET) 2 else 0)
        )

        TextWithSeekBar(
            TextV(textId = R.string.status_bar_battery_top_padding),
            SeekBarWithTextV("status_bar_battery_top_padding", -1, 20, 0)
        )

        TextWithSeekBar(
            TextV(textId = R.string.status_bar_battery_bottom_padding),
            SeekBarWithTextV("status_bar_battery_bottom_padding", -1, 20, 0)
        )
        Line()
        TextWithSeekBar(
            TextV(textId = R.string.status_bar_battery_left_margining),
            SeekBarWithTextV("status_bar_battery_left_margining", -20, 20, if (IS_TABLET) 1 else -7)
        )

        TextWithSeekBar(
            TextV(textId = R.string.status_bar_battery_right_margining),
            SeekBarWithTextV("status_bar_battery_right_margining", -20, 20, 0)
        )

        TextWithSeekBar(
            TextV(textId = R.string.status_bar_battery_top_margining),
            SeekBarWithTextV("status_bar_battery_top_margining", -20, 20, if (IS_TABLET) -20 else 0)
        )

        TextWithSeekBar(
            TextV(textId = R.string.status_bar_battery_bottom_margining),
            SeekBarWithTextV("status_bar_battery_bottom_margining", -20, 20, 0)
        )
    }
}