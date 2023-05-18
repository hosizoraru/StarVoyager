package star.sky.voyager.activity.pages

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SpinnerV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("scope_mi_pad", "MiPad", hideMenu = false)
class MaxMiPadPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_mi_pad)
        TitleText(textId = R.string.input)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.no_magic_pointer,
                tipsId = R.string.no_magic_pointer_tips
            ),
            SwitchV("no_magic_pointer", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.restore_esc,
                tipsId = R.string.restore_esc_tips
            ),
            SwitchV("restore_esc", false)
        )
        val bindingRemoveStylusBluetoothRestriction =
            GetDataBinding({
                MIUIActivity.safeSP.getBoolean("remove_stylus_bluetooth_restriction", true)
            }) { view, flags, data ->
                when (flags) {
                    1 -> view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
                }
            }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.remove_stylus_bluetooth_restriction,
                tipsId = R.string.remove_stylus_bluetooth_restriction_tips
            ),
            SwitchV(
                key = "remove_stylus_bluetooth_restriction",
                defValue = true,
                dataBindingSend = bindingRemoveStylusBluetoothRestriction.bindingSend
            )
        )
        TextSummaryWithSpinner(
            TextSummaryV(
                textId = R.string.remove_stylus_bluetooth_restriction_driver_version,
                tipsId = R.string.remove_stylus_bluetooth_restriction_driver_version_tips
            ),
            SpinnerV(
                MIUIActivity.safeSP.getString(
                    "remove_stylus_bluetooth_restriction_driver_version",
                    "2"
                )
            ) {
                add("1") {
                    MIUIActivity.safeSP.putAny(
                        "remove_stylus_bluetooth_restriction_driver_version",
                        "1"
                    )
                }
                add("2") {
                    MIUIActivity.safeSP.putAny(
                        "remove_stylus_bluetooth_restriction_driver_version",
                        "2"
                    )
                }
            },
            dataBindingRecv = bindingRemoveStylusBluetoothRestriction.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.ignore_stylus_key_gesture,
                tipsId = R.string.ignore_stylus_key_gesture_tips
            ),
            SwitchV("ignore_stylus_key_gesture", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.set_gesture_need_finger_num_to_4,
                tipsId = R.string.set_gesture_need_finger_num_to_4_tips
            ),
            SwitchV("set_gesture_need_finger_num_to_4", false)
        )
        Line()
        TitleText(textId = R.string.screen)
        val bindingDisableFixedOrientation =
            GetDataBinding({
                MIUIActivity.safeSP.getBoolean("disable_fixed_orientation", false)
            }) { view, flags, data ->
                when (flags) {
                    1 -> view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
                }
            }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.disable_fixed_orientation,
                tipsId = R.string.disable_fixed_orientation_tips
            ),
            SwitchV(
                key = "disable_fixed_orientation",
                defValue = false,
                dataBindingSend = bindingDisableFixedOrientation.bindingSend
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.disable_fixed_orientation_scope,
                tipsId = R.string.disable_fixed_orientation_scope_tips
            ) {
                showFragment("DisableFixedOrientationPage")
            },
            dataBindingRecv = bindingDisableFixedOrientation.getRecv(1)
        )
    }
}