package star.sky.voyager.activity.pages.sub

import android.view.View
import android.widget.Switch
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SeekBarWithTextV
import cn.fkj233.ui.activity.view.SpinnerV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R

@BMPage("status_bar_icon", "Status Bar Icon", hideMenu = false)
class StatusBarIconPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.status_bar_icon)
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.hide_icon,
                onClickListener = { showFragment("hide_icon") }
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.icon_position,
                onClickListener = { showFragment("icon_position") }
            )
        )
        TextWithSwitch(
            TextV(textId = R.string.show_wifi_standard),
            SwitchV("show_wifi_standard")
        )
        val wifiBinding = GetDataBinding({
            safeSP.getBoolean(
                "system_ui_use_new_hd",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_ui_use_new_hd,
                tipsId = R.string.system_ui_use_new_summary
            ),
            SwitchV(
                "system_ui_use_new_hd",
                false,
                dataBindingSend = wifiBinding.bindingSend
            )
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.no_show_on_wifi_connect,
                tipsId = R.string.no_show_on_wifi_hd
            ),
            SwitchV(
                "no_show_on_wifi",
                false
            ), dataBindingRecv = wifiBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.battery_percentage_font_size,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.battery_percentage_font_size)
                        setMessage(R.string.zero_do_no_change)
                        setEditText(
                            "", "${activity.getString(R.string.current)}${
                                safeSP.getFloat("battery_percentage_font_size", 0f)
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "battery_percentage_font_size",
                                    getEditText().toFloat()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                })
        )
        val customMobileTypeTextBinding = GetDataBinding({
            safeSP.getBoolean(
                "custom_mobile_type_text_switch",
                false
            )
        }) { view, flags, data ->
            when (flags) {
                1 -> (view as Switch).isEnabled = data as Boolean
                2 -> view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
            }
        }
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.custom_mobile_type_text_switch),
            SwitchV(
                "custom_mobile_type_text_switch",
                dataBindingSend = customMobileTypeTextBinding.bindingSend
            )
        )
        TextSummaryWithArrow(TextSummaryV(textId = R.string.custom_mobile_type_text) {
            MIUIDialog(activity) {
                setTitle(R.string.custom_mobile_type_text)
                setEditText(safeSP.getString("custom_mobile_type_text", "5G"), "")
                setLButton(textId = R.string.cancel) {
                    dismiss()
                }
                setRButton(textId = R.string.done) {
                    if (getEditText().isNotEmpty()) {
                        runCatching {
                            safeSP.putAny("custom_mobile_type_text", getEditText())
                            dismiss()
                            return@setRButton
                        }
                    }
                    makeText(activity, R.string.input_error, LENGTH_SHORT)
                        .show()
                }
            }.show()
        }, dataBindingRecv = customMobileTypeTextBinding.binding.getRecv(2))
        val bigMobileTypeIconBinding = GetDataBinding({
            safeSP.getBoolean(
                "big_mobile_type_icon",
                false
            )
        }) { view, flags, data ->
            when (flags) {
                1 -> (view as Switch).isEnabled = data as Boolean
                2 -> view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
            }
        }
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.big_mobile_type_icon),
            SwitchV(
                "big_mobile_type_icon",
                dataBindingSend = bigMobileTypeIconBinding.bindingSend
            )
        )
        val bigMobileTypeLocation: HashMap<Int, String> = hashMapOf<Int, String>().also {
            it[0] = getString(R.string.left)
            it[1] = getString(R.string.right)
        }
        TextWithSpinner(
            TextV(textId = R.string.big_mobile_type_location),
            SpinnerV(
                bigMobileTypeLocation[safeSP.getInt(
                    "big_mobile_type_location",
                    1
                )].toString()
            ) {
                add(bigMobileTypeLocation[0].toString()) {
                    safeSP.putAny("big_mobile_type_location", 0)
                }
                add(bigMobileTypeLocation[1].toString()) {
                    safeSP.putAny("big_mobile_type_location", 1)
                }
            },
            dataBindingRecv = bigMobileTypeIconBinding.binding.getRecv(2)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.big_mobile_type_only_show_network_card),
            SwitchV("big_mobile_type_only_show_network_card", false),
            dataBindingRecv = bigMobileTypeIconBinding.binding.getRecv(2)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.big_mobile_type_icon_bold),
            SwitchV("big_mobile_type_icon_bold", true),
            dataBindingRecv = bigMobileTypeIconBinding.binding.getRecv(2)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.big_mobile_type_icon_size,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.big_mobile_type_icon_size)
                        setEditText(
                            "",
                            "${activity.getString(R.string.def)}12.5, ${activity.getString(R.string.current)}${
                                safeSP.getFloat("big_mobile_type_icon_size", 12.5f)
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "big_mobile_type_icon_size",
                                    getEditText().toFloat()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = bigMobileTypeIconBinding.binding.getRecv(2)
        )
        TextSummaryWithArrow(TextSummaryV(textId = R.string.big_mobile_type_icon_up_and_down_position) {
            MIUIDialog(activity) {
                setTitle(R.string.big_mobile_type_icon_up_and_down_position)
                setMessage("${activity.getString(R.string.range)} -15~15")
                setEditText(
                    "",
                    "${activity.getString(R.string.def)}0, ${activity.getString(R.string.current)}${
                        safeSP.getInt("big_mobile_type_icon_up_and_down_position", 0)
                    }"
                )
                setLButton(textId = R.string.cancel) {
                    dismiss()
                }
                setRButton(textId = R.string.done) {
                    if (getEditText().isNotEmpty()) {
                        runCatching {
                            val value = getEditText().toInt()
                            if (value in (-15..15)) {
                                safeSP.putAny(
                                    "big_mobile_type_icon_up_and_down_position",
                                    value
                                )
                                dismiss()
                                return@setRButton
                            }
                        }
                    }
                    makeText(activity, R.string.input_error, LENGTH_SHORT)
                        .show()
                }
            }.show()
        }, dataBindingRecv = bigMobileTypeIconBinding.binding.getRecv(2))
        TextSummaryWithArrow(TextSummaryV(textId = R.string.big_mobile_type_icon_left_and_right_margins) {
            MIUIDialog(activity) {
                setTitle(R.string.big_mobile_type_icon_left_and_right_margins)
                setMessage("${activity.getString(R.string.range)} 0~30")
                setEditText(
                    "",
                    "${activity.getString(R.string.def)}0, ${activity.getString(R.string.current)}${
                        safeSP.getInt("big_mobile_type_icon_left_and_right_margins", 0)
                    }"
                )
                setLButton(textId = R.string.cancel) {
                    dismiss()
                }
                setRButton(textId = R.string.done) {
                    if (getEditText().isNotEmpty()) {
                        runCatching {
                            val value = getEditText().toInt()
                            if (value in (0..30)) {
                                safeSP.putAny(
                                    "big_mobile_type_icon_left_and_right_margins",
                                    value
                                )
                                dismiss()
                                return@setRButton
                            }
                        }
                    }
                    makeText(activity, R.string.input_error, LENGTH_SHORT)
                        .show()
                }
            }.show()
        }, dataBindingRecv = bigMobileTypeIconBinding.binding.getRecv(2))
        val maxBinding = GetDataBinding({
            safeSP.getBoolean(
                "max_notification_icon",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.max_notification_icon
            ),
            SwitchV(
                "max_notification_icon",
                false,
                dataBindingSend = maxBinding.bindingSend
            )
        )
        TextWithSeekBar(
            TextV(textId = R.string.maximum_number_of_notification_icons),
            SeekBarWithTextV(
                "maximum_number_of_notification_icons",
                1,
                20,
                3
            ),
            dataBindingRecv = maxBinding.binding.getRecv(1)
        )
        TextWithSeekBar(
            TextV(textId = R.string.maximum_number_of_notification_dots),
            SeekBarWithTextV(
                "maximum_number_of_notification_dots",
                0,
                4,
                3
            ),
            dataBindingRecv = maxBinding.binding.getRecv(1)
        )
        TextWithSeekBar(
            TextV(textId = R.string.maximum_number_of_lockscreen_notification_icons),
            SeekBarWithTextV(
                "maximum_number_of_lockscreen_notification_icons",
                1,
                20,
                3
            ),
            dataBindingRecv = maxBinding.binding.getRecv(1)
        )
    }
}