package star.sky.voyager.activity.pages.apps

import android.content.ComponentName
import android.content.Intent
import android.view.View
import android.widget.Toast
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R

//@SuppressLint("NonConstantResourceId")
//@BMPage("media_editor", titleId = R.string.scope_media_editor, hideMenu = false)
@BMPage("media_editor", "Media Editor", hideMenu = false)
class MediaEditorPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.scope_media_editor)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.filter_manager,
            ), SwitchV("filter_manager")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.unlock_unlimited_cropping,
                tipsId = R.string.unlock_unlimited_cropping_summary
            ), SwitchV("unlock_unlimited_cropping")
        )
        val deviceBinding = GetDataBinding({
            safeSP.getBoolean(
                "device_shell2",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.device_shell,
            ),
            SwitchV(
                "device_shell2",
                false,
                dataBindingSend = deviceBinding.bindingSend
            ),
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.your_product_market_name,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.device_shell)
                        setMessage(
                            "${activity.getString(R.string.def)}raphael\n${
                                activity.getString(
                                    R.string.current
                                )
                            }${
                                safeSP.getString(
                                    "device_shell_s2",
                                    "raphael"
                                )
                            }"
                        )
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "device_shell_s2",
                                    "raphael"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "device_shell_s2",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = deviceBinding.binding.getRecv(1)
        )
        val device2Binding = GetDataBinding({
            safeSP.getBoolean(
                "market_name",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.gallery_frame_market_name,
            ),
            SwitchV(
                "market_name",
                false,
                dataBindingSend = device2Binding.bindingSend
            ),
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.your_product_market_name,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.gallery_frame_market_name)
                        setMessage(
                            "${activity.getString(R.string.def)}Xiaomi 13 Ultra\n${
                                activity.getString(
                                    R.string.current
                                )
                            }${
                                safeSP.getString(
                                    "product_market_name",
                                    "Xiaomi 13 Ultra"
                                )
                            }"
                        )
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "product_market_name",
                                    "Xiaomi 13 Ultra"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "product_market_name",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = device2Binding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.scope_media_editor,
                onClickListener = {
                    try {
                        val intent = Intent()
                        val comp = ComponentName(
                            "com.miui.mediaeditor",
                            "com.miui.mediaeditor.MainActivity"
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