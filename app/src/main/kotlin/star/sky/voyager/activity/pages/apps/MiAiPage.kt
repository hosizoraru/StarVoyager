package star.sky.voyager.activity.pages.apps

import android.content.ComponentName
import android.content.Intent
import android.view.View
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R

@BMPage("scope_mi_ai", "Mi Ai", hideMenu = false)
class MiAiPage : BasePage() {
    override fun onCreate() {
        Page(
            activity.getDrawable(R.drawable.ic_miui_plus)!!,
            TextSummaryV(
                textId = R.string.scope_mi_smart_hub
            ), round = 8f,
            onClickListener = { showFragment("scope_mi_smart_hub") }
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
        val browserBinding = GetDataBinding({
            safeSP.getBoolean(
                "taplus_browser",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.taplus_browser
            ), SwitchV(
                "taplus_browser",
                false,
                dataBindingSend = browserBinding.bindingSend
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.taplus_browser_pkg,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.taplus_browser_pkg)
                        setMessage(
                            "${activity.getString(R.string.def)}com.android.browser\n${
                                activity.getString(
                                    R.string.current
                                )
                            }${
                                safeSP.getString(
                                    "taplus_browser_pkg",
                                    "com.android.browser"
                                )
                            }"
                        )
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "taplus_browser_pkg",
                                    "com.android.browser"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "taplus_browser_pkg",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = browserBinding.binding.getRecv(1)
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
        Line()
        TitleText(textId = R.string.scope_aireco)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.device_modify,
                tipsId = R.string.device_modify_summary,
            ), SwitchV("device_modify")
        )
        Line()
        TitleText(textId = R.string.scope_aiasst_vision)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.meeting_mode,
            ), SwitchV("meeting_mode")
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