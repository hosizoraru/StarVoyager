package star.sky.voyager.activity.pages.sub

import android.annotation.SuppressLint
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SeekBarWithTextV
import cn.fkj233.ui.activity.view.SpinnerV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R
import star.sky.voyager.utils.voyager.SafeSharedPreferences.getResourceString
import star.sky.voyager.utils.yife.Terminal.exec

@SuppressLint("NonConstantResourceId")
@BMPage("settings_mod", titleId = R.string.mod, hideMenu = false)
//@BMPage("settings_mod", "Settings Mod", hideMenu = false)
class SettingsModPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithSpinner(
            TextSummaryV(
                textId = R.string.trigger_condition,
            ),
            SpinnerV(
                safeSP.getResourceString(
                    "trigger_condition",
                    R.string.three_gesture_down
                ),
                dropDownWidth = 250F
            ) {
                add(R.string.long_press_power_key) {
                    safeSP.putAny("trigger_condition", "long_press_power_key")
                }
                add(R.string.double_knock) {
                    safeSP.putAny("trigger_condition", "double_knock")
                }
                add(R.string.three_gesture_down) {
                    safeSP.putAny("trigger_condition", "three_gesture_down")
                }
                add(R.string.key_combination_power_volume_down) {
                    safeSP.putAny(
                        "trigger_condition",
                        "key_combination_power_volume_down"
                    )
                }
                add(R.string.three_gesture_long_press) {
                    safeSP.putAny("trigger_condition", "three_gesture_long_press")
                }
                add(R.string.knock_slide_shape) {
                    safeSP.putAny("trigger_condition", "knock_slide_shape")
                }
                add(R.string.double_click_power_key) {
                    safeSP.putAny("trigger_condition", "double_click_power_key")
                }
                add(R.string.knock_long_press_horizontal_slid) {
                    safeSP.putAny(
                        "trigger_condition",
                        "knock_long_press_horizontal_slid"
                    )
                }
                add(R.string.knock_gesture_v) {
                    safeSP.putAny("trigger_condition", "knock_gesture_v")
                }
                add(R.string.back_double_tap) {
                    safeSP.putAny("trigger_condition", "back_double_tap")
                }
                add(R.string.back_triple_tap) {
                    safeSP.putAny("trigger_condition", "back_triple_tap")
                }
                add(R.string.fingerprint_double_tap) {
                    safeSP.putAny("trigger_condition", "fingerprint_double_tap")
                }
            },
        )
        TextSummaryWithSpinner(
            TextSummaryV(
                textId = R.string.trigger_action,
            ),
            SpinnerV(
                safeSP.getResourceString("trigger_action", R.string.none),
                dropDownWidth = 250F
            ) {
                add(R.string.launch_voice_assistant) {
                    safeSP.putAny("trigger_action", "launch_voice_assistant")
                }
                add(R.string.screen_shot) {
                    safeSP.putAny("trigger_action", "screen_shot")
                }
                add(R.string.partial_screen_shot) {
                    safeSP.putAny("trigger_action", "partial_screen_shot")
                }
                add(R.string.mi_pay) {
                    safeSP.putAny("trigger_action", "mi_pay")
                }
                add(R.string.launch_camera) {
                    safeSP.putAny("trigger_action", "launch_camera")
                }
                add(R.string.turn_on_torch) {
                    safeSP.putAny("trigger_action", "turn_on_torch")
                }
                add(R.string.change_brightness) {
                    safeSP.putAny("trigger_action", "change_brightness")
                }
                add(R.string.none) {
                    safeSP.putAny("trigger_action", "none")
                }
                add(R.string.launch_ai_shortcut) {
                    safeSP.putAny("trigger_action", "launch_ai_shortcut")
                }
                add(R.string.launch_calculator) {
                    safeSP.putAny("trigger_action", "launch_calculator")
                }
                add(R.string.mute) {
                    safeSP.putAny("trigger_action", "mute")
                }
                add(R.string.launch_alipay_payment_code) {
                    safeSP.putAny("trigger_action", "launch_alipay_payment_code")
                }
                add(R.string.launch_wechat_payment_code) {
                    safeSP.putAny("trigger_action", "launch_wechat_payment_code")
                }
                add(R.string.launch_alipay_health_code) {
                    safeSP.putAny("trigger_action", "launch_alipay_health_code")
                }
                add(R.string.launch_alipay_scanner) {
                    safeSP.putAny("trigger_action", "launch_alipay_scanner")
                }
                add(R.string.launch_wechat_scanner) {
                    safeSP.putAny("trigger_action", "launch_wechat_scanner")
                }
                add(R.string.dump_log) {
                    safeSP.putAny("trigger_action", "dump_log")
                }
                add(R.string.go_to_sleep) {
                    safeSP.putAny("trigger_action", "go_to_sleep")
                }
            },
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.put_setting_command,
                tipsId = R.string.put_setting_command_summary
            ) {
                val triggerCondition =
                    safeSP.getString("trigger_condition", "long_press_power_key")
                val triggerAction = safeSP.getString("trigger_action", "none")
                val command = "settings put system $triggerCondition $triggerAction"
                exec(command)
                makeText(
                    activity,
                    getString(R.string.finished),
                    LENGTH_SHORT
                ).show()
            }
        )
        Line()
        TitleText(textId = R.string.scale_unit)
        TextSummaryWithSeekBar(
            TextSummaryV(
                textId = R.string.window_animation_scale
            ), SeekBarWithTextV("window_animation_scale", 0, 40, 4)
        )
        TextSummaryWithSeekBar(
            TextSummaryV(
                textId = R.string.transition_animation_scale
            ), SeekBarWithTextV("transition_animation_scale", 0, 40, 4)
        )
        TextSummaryWithSeekBar(
            TextSummaryV(
                textId = R.string.animator_duration_scale
            ), SeekBarWithTextV("animator_duration_scale", 0, 40, 4)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.scale_command,
                tipsId = R.string.put_setting_command_summary
            ) {
                val wScale =
                    safeSP.getInt("window_animation_scale", 4) * 0.25.toFloat()
                val tScale =
                    safeSP.getInt("transition_animation_scale", 4) * 0.25.toFloat()
                val aScale =
                    safeSP.getInt("animator_duration_scale", 4) * 0.25.toFloat()
                val command1 = "settings put global window_animation_scale $wScale"
                val command2 = "settings put global transition_animation_scale $tScale"
                val command3 = "settings put global animator_duration_scale $aScale"
                exec(arrayOf(command1, command2, command3))
                makeText(
                    activity,
                    getString(R.string.finished),
                    LENGTH_SHORT
                ).show()
            }
        )
        Line()
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.screen_off_timeout,
                tipsId = R.string.screen_off_timeout_summary,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.screen_off_timeout)
                        setMessage(
                            "${activity.getString(R.string.def)}30，${activity.getString(R.string.current)}${
                                safeSP.getInt("screen_off_timeout_times", 30)
                            }"
                        )
                        setEditText("", activity.getString(R.string.screen_off_timeout_summary))
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText().isNotEmpty()) {
                                runCatching {
                                    safeSP.putAny(
                                        "screen_off_timeout_times",
                                        getEditText().toInt()
                                    )
                                }.onFailure {
                                    makeText(activity, "Input error", LENGTH_LONG)
                                        .show()
                                }
                            }
                            dismiss()
                        }
                    }.show()
                })
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.screen_off_timeout_command,
                tipsId = R.string.put_setting_command_summary
            ) {
                val timeout = safeSP.getInt("screen_off_timeout_times", 30) * 1000
                val command1 = "settings put system screen_off_timeout $timeout"
                exec(command1)
                makeText(
                    activity,
                    getString(R.string.finished),
                    LENGTH_SHORT
                ).show()
            }
        )
    }
}