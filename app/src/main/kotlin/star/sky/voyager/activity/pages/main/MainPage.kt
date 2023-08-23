package star.sky.voyager.activity.pages.main

import android.annotation.SuppressLint
import cn.fkj233.ui.activity.annotation.BMMainPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

//@SuppressLint("NonConstantResourceId")
//@BMMainPage(titleId = R.string.app_name)
@BMMainPage("StarVoyager")
class MainPage : BasePage() {
    @SuppressLint("WorldReadableFiles")
    override fun onCreate() {
//        TitleText(textId = R.string.scope)
        Page(
            activity.getDrawable(R.drawable.ic_systemui_13)!!,
            TextSummaryV(
                textId = R.string.scope_system_ui
            ), round = 8f,
            onClickListener = { showFragment("scope_system_ui") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_android)!!,
            TextSummaryV(
                textId = R.string.scope_android,
                tipsId = R.string.scope_android_summary
            ), round = 8f,
            onClickListener = { showFragment("scope_android") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_miuihome)!!,
            TextSummaryV(
                textId = R.string.scope_miui_home
            ), round = 8f,
            onClickListener = { showFragment("scope_miui_home") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_securitycenter)!!,
            TextSummaryV(
                textId = R.string.scope_security_center
            ), round = 8f,
            onClickListener = { showFragment("scope_security_center") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_powerkeeper)!!,
            TextSummaryV(
                textId = R.string.scope_power_keeper
            ), round = 8f,
            onClickListener = { showFragment("scope_power_keeper") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_settings)!!,
            TextSummaryV(
                textId = R.string.scope_settings,
            ), round = 8f,
            onClickListener = { showFragment("scope_settings") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_packageinstaller)!!,
            TextSummaryV(
                textId = R.string.scope_app_manager
            ), round = 8f,
            onClickListener = { showFragment("scope_pkg_installer") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_gallery)!!,
            TextSummaryV(
                textId = R.string.scope_gallery
            ), round = 8f,
            onClickListener = { showFragment("scope_gallery") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_miai)!!,
            TextSummaryV(
                textId = R.string.scope_mi_ai
            ), round = 8f,
            onClickListener = { showFragment("scope_mi_ai") }
        )
        Page(
            activity.getDrawable(R.drawable.ic_maxmipad)!!,
            TextSummaryV(
                textId = R.string.scope_mi_pad
            ), round = 8f,
            onClickListener = { showFragment("scope_mi_pad") }
        )
        Line()
//        TitleText(textId = R.string.about)
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.about_module,
                tips = getString(R.string.about_module_summary),
                onClickListener = { showFragment("about_module") })
        )
    }
}