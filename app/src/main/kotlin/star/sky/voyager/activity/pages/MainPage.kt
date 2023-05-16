package star.sky.voyager.activity.pages

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.pm.PackageManager
import cn.fkj233.ui.activity.annotation.BMMainPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.BuildConfig
import star.sky.voyager.R

@SuppressLint("NonConstantResourceId")
@BMMainPage("StarVoyager")
class MainPage : BasePage() {
    @SuppressLint("WorldReadableFiles")
    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.HideLauncherIcon,
                colorId = R.color.blue
            ),
            SwitchV("hLauncherIcon", onClickListener = {
                activity.packageManager.setComponentEnabledSetting(
                    ComponentName(activity, "${BuildConfig.APPLICATION_ID}.launcher"),
                    if (it) {
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    } else {
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                    },
                    PackageManager.DONT_KILL_APP
                )
            })
        )
        Line()
        TitleText(textId = R.string.scope)
        Page(
            activity.getDrawable(R.drawable.ic_systemui_13)!!,
            pageNameId = R.string.scope_system_ui, round = 8f,
            onClickListener = { showFragment("scope_system_ui") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_android)!!,
            pageNameId = R.string.scope_android, round = 8f,
            onClickListener = { showFragment("scope_android") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_miuihome)!!,
            pageNameId = R.string.scope_miui_home, round = 8f,
            onClickListener = { showFragment("scope_miui_home") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_securitycenter)!!,
            pageNameId = R.string.scope_security_center,
            round = 8f,
            onClickListener = { showFragment("scope_security_center") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_powerkeeper)!!,
            pageNameId = R.string.scope_power_keeper, round = 8f,
            onClickListener = { showFragment("scope_power_keeper") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_packageinstaller)!!,
            pageNameId = R.string.scope_app_manager, round = 8f,
            onClickListener = { showFragment("scope_pkg_installer") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_gallery)!!,
            pageNameId = R.string.scope_gallery, round = 8f,
            onClickListener = { showFragment("scope_gallery") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_miai)!!,
            pageNameId = R.string.scope_mi_ai, round = 8f,
            onClickListener = { showFragment("scope_mi_ai") }
        )
        Page(
            activity.getDrawable(R.drawable.miui_plus)!!,
            pageNameId = R.string.scope_mi_smart_hub, round = 8f,
            onClickListener = { showFragment("scope_mi_smart_hub") }
        )
        Line()
        TitleText(textId = R.string.about)
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.about_module,
                tips = getString(R.string.about_module_summary),
                onClickListener = { showFragment("about_module") })
        )
    }
}