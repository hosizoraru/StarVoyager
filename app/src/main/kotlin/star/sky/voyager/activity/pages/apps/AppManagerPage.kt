package star.sky.voyager.activity.pages.apps

import android.annotation.SuppressLint
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SpinnerV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@SuppressLint("NonConstantResourceId")
@BMPage("scope_pkg_installer", titleId = R.string.scope_app_manager, hideMenu = false)
class AppManagerPage : BasePage() {
    override fun onCreate() {
        Page(
            activity.getDrawable(R.drawable.ic_package_installer)!!,
            TextSummaryV(
                textId = R.string.scope_pkg_installer,
            ), round = 8f,
            onClickListener = { showFragment("package_installer") }
        )
        Line()
        TitleText(textId = R.string.scope_market)
        TextSummaryWithSpinner(
            TextSummaryV(
                textId = R.string.Market_As,
            ),
            SpinnerV(safeSP.getString("Market_As", "Default"), dropDownWidth = 200F) {
                add("Default") {
                    safeSP.putAny("Market_As", "Default")
                }
                add("Mi13Pro") {
                    safeSP.putAny("Market_As", "Mi13Pro")
                }
                add("Mi13Ultra") {
                    safeSP.putAny("Market_As", "Mi13Ultra")
                }
                add("MiPad6Pro") {
                    safeSP.putAny("Market_As", "MiPad6Pro")
                }
                add("MiPad6Max") {
                    safeSP.putAny("Market_As", "MiPad6Max")
                }
                add("MixFold3") {
                    safeSP.putAny("Market_As", "MixFold3")
                }
            },
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.remove_open_ad),
            SwitchV("remove_market_ad")
        )
        Line()
        TitleText(textId = R.string.scope_file_explorer)
        TextSSw(
            getString(R.string.file_explorer_can_selectable),
            key = "file_explorer_can_selectable"
        )
        TextSSw(
            getString(R.string.file_explorer_is_single_line),
            key = "file_explorer_is_single_line"
        )
        Line()
        TitleText(textId = R.string.scope_music)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.remove_open_ad),
            SwitchV("remove_open_ad")
        )
        Line()
        TitleText(textId = R.string.scope_providers_downloads)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.remove_xl_download),
            SwitchV("remove_xl_download")
        )
        Line()
        TitleText(textId = R.string.scope_aod)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.unlock_always_on_display_time,
            ), SwitchV("unlock_always_on_display_time")
        )
        Line()
        TitleText(textId = R.string.scope_wallpaper)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.unlock_super_wallpaper,
            ), SwitchV("unlock_super_wallpaper")
        )
        Line()
        TitleText(textId = R.string.scope_weather)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.high_animation,
            ), SwitchV("high_animation")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.more_card,
            ), SwitchV("more_card")
        )
        Line()
        TitleText(textId = R.string.scope_external_storage)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.No_Storage_Restrict),
            SwitchV("No_Storage_Restrict")
        )
        Line()
        TitleText(textId = R.string.scope_lbe)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.hide_miui_clipboard_dialog),
            SwitchV("hide_miui_clipboard_dialog")
        )
        Line()
        TitleText(textId = R.string.scope_update)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.remove_ota_validate,
                tipsId = R.string.remove_ota_validate_summary
            ), SwitchV("remove_ota_validate")
        )
        Line()
        TitleText(textId = R.string.scope_guard_provider)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.Anti_Defraud_App_Manager),
            SwitchV("Anti_Defraud_App_Manager")
        )
    }
}