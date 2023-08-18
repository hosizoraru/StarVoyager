package star.sky.voyager.activity.pages

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@BMPage("scanner", "Scanner", hideMenu = false)
class ScannerPage : BasePage() {
    override fun onCreate() {
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
            safeSP.getBoolean(
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
    }
}