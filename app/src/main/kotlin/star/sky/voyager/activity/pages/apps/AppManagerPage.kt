package star.sky.voyager.activity.pages.apps

import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SpinnerV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("scope_pkg_installer", "App Manager", hideMenu = false)
class AppManagerPage : BasePage() {
    override fun onCreate() {
        Page(
            activity.getDrawable(R.drawable.ic_package_installer)!!,
            pageNameId = R.string.scope_pkg_installer, round = 8f,
            onClickListener = { showFragment("package_installer") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_settings)!!,
            pageNameId = R.string.scope_settings, round = 8f,
            onClickListener = { showFragment("scope_settings") }
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
                add("MiPad5Pro12.4") {
                    safeSP.putAny("Market_As", "MiPad5Pro12.4")
                }
                add("MiPad6Pro") {
                    safeSP.putAny("Market_As", "MiPad6Pro")
                }
                add("MixFold2") {
                    safeSP.putAny("Market_As", "MixFold2")
                }
            },
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
        Line()
        TitleText(textId = R.string.scope_music)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.remove_open_ad),
            SwitchV("remove_open_ad")
        )
    }
}