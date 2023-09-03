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

@SuppressLint("NonConstantResourceId")
@BMPage("settings_unlock", titleId = R.string.unlock, hideMenu = false)
//@BMPage("settings_unlock", "Settings Unlock", hideMenu = false)
class SettingsUnlockPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.pad_area
            ), SwitchV("pad_area")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.mi_gboard
            ), SwitchV("mi_gboard")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.unlock_taplus
            ), SwitchV("unlock_taplus")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.hp_location,
                tipsId = R.string.require_hardware_support,
            ), SwitchV("hp_location")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.ime_bottom
            ), SwitchV("ime_bottom")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.mech_keyboard
            ), SwitchV("mech_keyboard")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.oled_never_time_out
            ), SwitchV("oled_never_time_out")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.new_nfc_page,
                tipsId = R.string.new_nfc_page_summary
            ), SwitchV("new_nfc_page")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.novelty_haptic,
                tipsId = R.string.novelty_haptic_summary
            ), SwitchV("novelty_haptic")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.speed_mode,
                tipsId = R.string.speed_mode_summary
            ), SwitchV("speed_mode")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.show_vip_service
            ), SwitchV("show_vip_service")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.voip_assistant
            ), SwitchV("voip_assistant")
        )
        val batteryStyleBinding = GetDataBinding({
            safeSP.getBoolean(
                "battery_style",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.battery_style
            ), SwitchV("battery_style", dataBindingSend = batteryStyleBinding.bindingSend)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.battery_style_top_color,
            ), SwitchV("battery_style_top_color", false),
            dataBindingRecv = batteryStyleBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.normal_color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.normal_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "top_battery_normal_color",
                                    "#FFFFFF"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "top_battery_normal_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = batteryStyleBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.low_level_color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.low_level_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "top_battery_low_level_color",
                                    "#FFFFFF"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "top_battery_low_level_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = batteryStyleBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.power_save_color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.power_save_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "top_battery_power_save_color",
                                    "#FFFFFF"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "top_battery_power_save_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = batteryStyleBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.custom_width,
            ), SwitchV("top_battery_custom_width", false),
            dataBindingRecv = batteryStyleBinding.binding.getRecv(1)
        )
        TextWithSeekBar(
            TextV(textId = R.string.width),
            SeekBarWithTextV("top_battery_width", -10, 10, -1),
            dataBindingRecv = batteryStyleBinding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.custom_height,
            ), SwitchV("top_battery_custom_height", false),
            dataBindingRecv = batteryStyleBinding.binding.getRecv(1)
        )
        TextWithSeekBar(
            TextV(textId = R.string.height),
            SeekBarWithTextV("top_battery_height", -10, 20, 4),
            dataBindingRecv = batteryStyleBinding.getRecv(1)
        )
    }
}