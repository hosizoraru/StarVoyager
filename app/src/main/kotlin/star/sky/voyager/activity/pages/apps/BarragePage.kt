package star.sky.voyager.activity.pages.apps

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SeekBarWithTextV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("barrage", "Barrage", hideMenu = false)
class BarragePage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_barrage)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.any_app_barrage
            ),
            SwitchV("any_app_barrage", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.any_where_barrage
            ),
            SwitchV("any_where_barrage", false)
        )
        val barrageLengthBinding = GetDataBinding({
            safeSP.getBoolean(
                "modify_barrage_length",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.modify_barrage_length,
            ),
            SwitchV(
                "modify_barrage_length",
                false,
                dataBindingSend = barrageLengthBinding.bindingSend
            )
        )
        TextSummaryWithSeekBar(
            TextSummaryV(
                textId = R.string.barrage_length
            ),
            SeekBarWithTextV(
                "barrage_length",
                18,
                108,
                36
            ),
            dataBindingRecv = barrageLengthBinding.binding.getRecv(1)
        )
    }
}