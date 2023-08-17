package star.sky.voyager.activity.pages.sub

import android.content.ComponentName
import android.content.Intent
import android.view.View
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SeekBarWithTextV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("security_unlock", "Security Unlock", hideMenu = false)
class SecurityUnlockPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_security_center)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.screen_time,
                tipsId = R.string.screen_time_summary
            ),
            SwitchV("screen_time", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.MEMC,
                tipsId = R.string.require_hardware_support
            ),
            SwitchV("MEMC", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.enhance_contours,
                tipsId = R.string.require_hardware_support
            ),
            SwitchV("enhance_contours", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.super_resolution,
                tipsId = R.string.require_hardware_support
            ),
            SwitchV("super_resolution", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.vcb_ability
            ),
            SwitchV("vcb_ability", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.privacy_camera
            ),
            SwitchV("privacy_camera", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.camera_face_tracker
            ),
            SwitchV("camera_face_tracker", false)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.front_camera_assistant,
                onClickListener = {
                    try {
                        val intent = Intent()
                        val comp = ComponentName(
                            "com.miui.securitycenter",
                            "com.miui.gamebooster.beauty.BeautySettingsActivity"
                        )
                        intent.component = comp
                        activity.startActivity(intent)
                    } catch (e: Exception) {
                        makeText(activity, "启动失败，可能是不支持", LENGTH_LONG).show()
                    }
                })
        )
        Line()
        TitleText(textId = R.string.scope_barrage)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.any_app_barrage
            ),
            SwitchV("any_app_barrage", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.any_where_barrage
            ),
            SwitchV("any_where_barrage", false)
        )
        val barrageLengthBinding = GetDataBinding({
            safeSP.getBoolean(
                "modify_barrage_length",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.modify_barrage_length,
            ),
            SwitchV(
                "modify_barrage_length",
                false,
                dataBindingSend = barrageLengthBinding.bindingSend
            )
        )
        TextSummaryWithSeekBar(
            TextSummaryV(
                textId = R.string.barrage_length
            ),
            SeekBarWithTextV(
                "barrage_length",
                18,
                108,
                36
            ),
            dataBindingRecv = barrageLengthBinding.binding.getRecv(1)
        )
    }
}