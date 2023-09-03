package star.sky.voyager.activity.pages.apps

import android.annotation.SuppressLint
import android.view.View
import android.widget.Switch
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R

@SuppressLint("NonConstantResourceId")
@BMPage("scope_android", titleId = R.string.scope_android, hideMenu = false)
//@BMPage("scope_android", "System FrameWork", hideMenu = false)
class AndroidPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.core_patch)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.down_grade,
                tipsId = R.string.down_grade_summary
            ), SwitchV("down_grade")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.auth_creak,
                tipsId = R.string.auth_creak_summary
            ), SwitchV("auth_creak")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.digestCreak,
                tipsId = R.string.digestCreak_summary
            ), SwitchV("digestCreak")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.UsePreSig,
                tipsId = R.string.UsePreSig_summary
            ), SwitchV("UsePreSig")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.enhancedMode,
                tipsId = R.string.enhancedMode_summary
            ), SwitchV("enhancedMode")
        )
        Line()
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.aosp_share_sheet
            ), SwitchV("aosp_share_sheet")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.dark_mode_for_all_apps,
                tipsId = R.string.dark_mode_for_all_apps_summary
            ), SwitchV("dark_mode_for_all_apps")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.disable_flag_secure,
                tipsId = R.string.disable_flag_secure_summary
            ), SwitchV("disable_flag_secure")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.delete_on_post_notification,
                tipsId = R.string.delete_on_post_notification_summary
            ), SwitchV("delete_on_post_notification")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.remove_small_window_restrictions,
                tipsId = R.string.remove_small_window_restrictions_summary
            ), SwitchV("remove_small_window_restrictions")
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.max_wallpaper_scale,
                tipsId = R.string.max_wallpaper_scale_summary,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.max_wallpaper_scale)
                        setMessage(
                            "${activity.getString(R.string.def)}1.2，${activity.getString(R.string.current)}${
                                safeSP.getFloat("max_wallpaper_scale", 1.2f)
                            }"
                        )
                        setEditText("", "${activity.getString(R.string.range)}1.0-2.0")
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText().isNotEmpty()) {
                                runCatching {
                                    safeSP.putAny(
                                        "max_wallpaper_scale",
                                        getEditText().toFloat()
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
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.allow_untrusted_touches,
                tipsId = R.string.take_effect_after_reboot
            ), SwitchV("allow_untrusted_touches")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.kill_domain_verification,
                tipsId = R.string.kill_domain_verification_summary
            ), SwitchV("kill_domain_verification")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.disable_72h_verify
            ), SwitchV("disable_72h_verify")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.max_free_form
            ), SwitchV("max_free_form")
        )
        Line()
        TitleText(textId = R.string.sound)
        val mediaVolumeStepsSwitchBinding = GetDataBinding({
            safeSP.getBoolean(
                "media_volume_steps_switch",
                false
            )
        }) { view, flags, data ->
            when (flags) {
                1 -> (view as Switch).isEnabled = data as Boolean
                2 -> view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
            }
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.media_volume_steps_switch,
                tips = "${getString(R.string.take_effect_after_reboot)}\n${getString(R.string.media_volume_steps_summary)}"
            ),
            SwitchV(
                "media_volume_steps_switch",
                dataBindingSend = mediaVolumeStepsSwitchBinding.bindingSend
            )
        )
        SeekBarWithText(
            "media_volume_steps",
            15,
            29,
            15,
            dataBindingRecv = mediaVolumeStepsSwitchBinding.binding.getRecv(2)
        )
    }
}