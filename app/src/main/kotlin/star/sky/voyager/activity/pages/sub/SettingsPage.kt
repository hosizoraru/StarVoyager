package star.sky.voyager.activity.pages.sub

import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SpinnerV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R
import star.sky.voyager.utils.yife.SafeSharedPreferences.getResourceString
import star.sky.voyager.utils.yife.Terminal.exec

@BMPage("scope_settings", "Settings", hideMenu = false)
class SettingsPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_settings)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.show_notification_importance,
                tipsId = R.string.show_notification_importance_summary
            ), SwitchV("show_notification_importance")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.speed_mode,
                tipsId = R.string.speed_mode_summary
            ), SwitchV("speed_mode")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_settings_permission_unknown_origin_app,
                tipsId = R.string.system_settings_permission_unknown_origin_app_desc
            ), SwitchV("system_settings_permission_unknown_origin_app")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.no_through_the_list,
            ), SwitchV("no_through_the_list")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.new_nfc_page,
                tipsId = R.string.new_nfc_page_summary
            ), SwitchV("new_nfc_page")
        )
        Line()
        TextSummaryWithSpinner(
            TextSummaryV(
                textId = R.string.trigger_condition,
            ),
            SpinnerV(
                safeSP.getResourceString("trigger_condition", R.string.three_gesture_down),
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
                    safeSP.putAny("trigger_condition", "key_combination_power_volume_down")
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
                    safeSP.putAny("trigger_condition", "knock_long_press_horizontal_slid")
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
            },
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.put_setting_command,
                tipsId = R.string.put_setting_command_summary
            ) {
                val triggerCondition = safeSP.getString("trigger_condition", "long_press_power_key")
                val triggerAction = safeSP.getString("trigger_action", "setting_none")
                val command = "settings put system $triggerCondition $triggerAction"
                exec(command)
                makeText(
                    activity,
                    getString(R.string.finished),
                    LENGTH_SHORT
                ).show()
            }
        )
    }
}