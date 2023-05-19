package star.sky.voyager.activity.pages

import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SpinnerV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("scope_pkg_installer", "App Manager", hideMenu = false)
class AppManagerPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_market)
        TextSummaryWithSpinner(
            TextSummaryV(
                textId = R.string.Market_As,
            ),
            SpinnerV(MIUIActivity.safeSP.getString("Market_As", "Default"), dropDownWidth = 200F) {
                add("Default") {
                    MIUIActivity.safeSP.putAny("Market_As", "Default")
                }
                add("Mi13Pro") {
                    MIUIActivity.safeSP.putAny("Market_As", "Mi13Pro")
                }
                add("Mi13Ultra") {
                    MIUIActivity.safeSP.putAny("Market_As", "Mi13Ultra")
                }
                add("MiPad5Pro12.4") {
                    MIUIActivity.safeSP.putAny("Market_As", "MiPad5Pro12.4")
                }
                add("MiPad6Pro") {
                    MIUIActivity.safeSP.putAny("Market_As", "MiPad6Pro")
                }
                add("MixFold2") {
                    MIUIActivity.safeSP.putAny("Market_As", "MixFold2")
                }
            },
        )
        Line()
        TitleText(textId = R.string.scope_pkg_installer)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.package_installer_remove_check),
            SwitchV("package_installer_remove_check")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.package_installer_remove_ads),
            SwitchV("package_installer_remove_ads")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.Disable_Safe_Model_Tip),
            SwitchV("Disable_Safe_Model_Tip")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.package_installer_all_as_system_app,
            ),
            SwitchV("package_installer_all_as_system_app")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.package_installer_show_more_apk_info),
            SwitchV("package_installer_show_more_apk_info")
        )
        Line()
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
    }
}