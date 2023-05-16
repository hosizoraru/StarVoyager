package star.sky.voyager.activity.pages

import android.content.ComponentName
import android.content.Intent
import android.widget.Toast
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("scope_power_keeper", "PowerKeeper", hideMenu = false)
class PowerKeeperPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_power_keeper)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.custom_refresh_rate,
            ), SwitchV("custom_refresh_rate")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.do_not_clear_app,
            ), SwitchV("do_not_clear_app")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.do_not_clear_app_plus,
            ), SwitchV("do_not_clear_app_plus")
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.battery_optimization,
                tipsId = R.string.battery_optimization_summary,
                onClickListener = {
                    try {
                        val intent = Intent()
                        val comp = ComponentName(
                            "com.android.settings",
                            "com.android.settings.Settings\$HighPowerApplicationsActivity"
                        )
                        intent.component = comp
                        activity.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(activity, "启动失败，可能是不支持", Toast.LENGTH_LONG).show()
                    }
                })
        )
    }
}