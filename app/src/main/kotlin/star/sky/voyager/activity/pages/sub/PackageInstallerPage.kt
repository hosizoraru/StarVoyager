package star.sky.voyager.activity.pages.sub

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R

@BMPage("package_installer", "Package Installer", hideMenu = false)
class PackageInstallerPage : BasePage() {
    override fun onCreate() {
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
                textId = R.string.all_as_system_app,
            ),
            SwitchV("all_as_system_app")
        )
        val yourSourceBinding = GetDataBinding({
            safeSP.getBoolean(
                "custom_installation_source",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.custom_installation_source,
            ),
            SwitchV(
                "custom_installation_source",
                false,
                dataBindingSend = yourSourceBinding.bindingSend
            ),
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.your_source,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.your_source)
                        setMessage(
                            "${activity.getString(R.string.def)}com.android.fileexplorer\n${
                                activity.getString(
                                    R.string.current
                                )
                            }${
                                safeSP.getString(
                                    "your_source",
                                    "com.android.fileexplorer"
                                )
                            }"
                        )
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "your_source",
                                    "com.android.fileexplorer"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "your_source",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = yourSourceBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.package_installer_show_more_apk_info),
            SwitchV("package_installer_show_more_apk_info")
        )
    }
}