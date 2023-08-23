package star.sky.voyager.activity.pages.apps

import android.content.ComponentName
import android.content.Intent
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

//@SuppressLint("NonConstantResourceId")
//@BMPage("scope_mi_ai", titleId = R.string.scope_mi_ai, hideMenu = false)
@BMPage("scope_mi_ai", "Mi AI", hideMenu = false)
class MiAiPage : BasePage() {
    override fun onCreate() {
        Page(
            activity.getDrawable(R.drawable.ic_miui_plus)!!,
            TextSummaryV(
                textId = R.string.scope_mi_smart_hub
            ), round = 8f,
            onClickListener = { showFragment("scope_mi_smart_hub") }
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.scope_scanner,
                onClickListener = { showFragment("scanner") }
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.scope_ta_plus,
                onClickListener = { showFragment("taplus") }
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.scope_aiasst_vision,
                onClickListener = { showFragment("aiasst_vision") }
            )
        )
        Line()
        TitleText(textId = R.string.scope_cloud_service)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.unlock_widevine_l1,
            ), SwitchV("unlock_widevine_l1")
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.widevine_l1,
                onClickListener = {
                    try {
                        val intent = Intent()
                        val comp = ComponentName(
                            "com.miui.cloudservice",
                            "com.miui.cloudservice.alipay.provision.FingerprintListActivity"
                        )
                        intent.component = comp
                        activity.startActivity(intent)
                    } catch (e: Exception) {
                        makeText(activity, "启动失败，可能是不支持", LENGTH_LONG).show()
                    }
                })
        )
        Line()
        TitleText(textId = R.string.scope_miui_creation)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.creation_phone,
            ), SwitchV("creation_phone")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.creation_image_size,
            ), SwitchV("creation_image_size")
        )
        Line()
        TitleText(textId = R.string.scope_aireco)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.device_modify,
                tipsId = R.string.device_modify_summary,
            ), SwitchV("device_modify")
        )
    }
}