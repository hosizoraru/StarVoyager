package star.sky.voyager.activity.pages.apps

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("scope_mi_ai", "Mi Ai", hideMenu = false)
class MiAiPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_aireco)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.device_modify,
                tipsId = R.string.device_modify_summary,
            ), SwitchV("device_modify")
        )
        Line()
        TitleText(textId = R.string.scope_scanner)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.card,
            ), SwitchV("card")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.doc_ppt,
            ), SwitchV("doc_ppt")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.ocr2,
            ), SwitchV("ocr2")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.translation,
            ), SwitchV("translation")
        )
        val documentBinding = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
                "document",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.document
            ), SwitchV(
                "document",
                false,
                dataBindingSend = documentBinding.bindingSend
            )
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.excel),
            SwitchV("excel", false),
            dataBindingRecv = documentBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.ppt),
            SwitchV("ppt", false),
            dataBindingRecv = documentBinding.binding.getRecv(1)
        )
        Line()
        TitleText(textId = R.string.scope_ta_plus)
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.horizontal_content_extension),
            SwitchV("horizontal_content_extension")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.unlock_taplus_for_pad,
                tipsId = R.string.open_on_demand
            ),
            SwitchV("unlock_taplus_for_pad")
        )
    }
}