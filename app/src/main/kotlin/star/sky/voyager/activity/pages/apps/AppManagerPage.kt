package star.sky.voyager.activity.pages.apps

import android.annotation.SuppressLint
import android.view.View
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SpinnerV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R

@SuppressLint("NonConstantResourceId")
@BMPage("scope_pkg_installer", titleId = R.string.scope_app_manager, hideMenu = false)
//@BMPage("scope_pkg_installer", "Application Management", hideMenu = false)
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
        val deviceMarketBinding = GetDataBinding({
            safeSP.getBoolean(
                "device_market",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        val deviceMarketRecommendBinding = GetDataBinding({
            safeSP.getBoolean(
                "device_market_recommend",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        val deviceMarketModBinding = GetDataBinding({
            safeSP.getBoolean(
                "device_market_mod",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.device_market,
            ),
            SwitchV(
                "device_market",
                false,
                dataBindingSend = deviceMarketBinding.bindingSend
            ),
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.device_market_recommend,
            ),
            SwitchV(
                "device_market_recommend",
                false,
                dataBindingSend = deviceMarketRecommendBinding.bindingSend
            ), deviceMarketBinding.binding.getRecv(1)
        )
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
            }, deviceMarketRecommendBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.device_market_mod,
                tipsId = R.string.device_market_mod_summary,
            ),
            SwitchV(
                "device_market_mod",
                false,
                dataBindingSend = deviceMarketModBinding.bindingSend
            ), deviceMarketBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.your_product_market_name,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.device_market_mod)
                        setMessage(
                            "${activity.getString(R.string.def)}ishtar\n${
                                activity.getString(
                                    R.string.current
                                )
                            }${
                                safeSP.getString(
                                    "market_as_device",
                                    "ishtar"
                                )
                            }"
                        )
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "market_as_device",
                                    "ishtar"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "market_as_device",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = deviceMarketModBinding.binding.getRecv(1)
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
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.global_weather_flag,
            ), SwitchV("global_weather_flag")
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