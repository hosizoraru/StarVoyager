package star.sky.voyager.activity.pages.sub

import android.annotation.SuppressLint
import android.view.View
import android.widget.Switch
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SpinnerV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextV
import star.sky.voyager.R

@SuppressLint("NonConstantResourceId")
@BMPage("status_bar_network_speed", titleId = R.string.status_bar_network_speed, hideMenu = false)
//@BMPage("status_bar_network_speed", "Status Bar Network Speed", hideMenu = false)
class StatusBarNetWorkSpeedPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.status_bar_network_speed)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.status_bar_network_speed_refresh_speed,
                tipsId = R.string.status_bar_network_speed_refresh_speed_summary
            ), SwitchV("status_bar_network_speed_refresh_speed")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.hide_status_bar_network_speed_second,
                tipsId = R.string.hide_status_bar_network_speed_second_summary
            ), SwitchV("hide_status_bar_network_speed_second")
        )
        TextWithSwitch(
            TextV(textId = R.string.hide_network_speed_splitter),
            SwitchV("hide_network_speed_splitter")
        )
        val statusBarDualRowNetworkSpeedBinding = GetDataBinding({
            safeSP.getBoolean(
                "status_bar_dual_row_network_speed",
                false
            )
        }) { view, flags, data ->
            when (flags) {
                1 -> (view as Switch).isEnabled = data as Boolean
                2 -> view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
            }
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.status_bar_dual_row_network_speed,
                tipsId = R.string.status_bar_dual_row_network_speed_summary
            ),
            SwitchV(
                "status_bar_dual_row_network_speed",
                dataBindingSend = statusBarDualRowNetworkSpeedBinding.bindingSend
            )
        )
        val align: HashMap<Int, String> = hashMapOf()
        align[0] = getString(R.string.left)
        align[1] = getString(R.string.right)
        TextWithSpinner(
            TextV(textId = R.string.status_bar_network_speed_dual_row_gravity),
            SpinnerV(
                align[safeSP.getInt(
                    "status_bar_network_speed_dual_row_gravity",
                    0
                )].toString()
            ) {
                add(align[0].toString()) {
                    safeSP.putAny("status_bar_network_speed_dual_row_gravity", 0)
                }
                add(align[1].toString()) {
                    safeSP.putAny("status_bar_network_speed_dual_row_gravity", 1)

                }
            },
            dataBindingRecv = statusBarDualRowNetworkSpeedBinding.binding.getRecv(2)
        )
        TextWithSpinner(
            TextV(textId = R.string.status_bar_network_speed_dual_row_icon),
            SpinnerV(
                safeSP.getString(
                    "status_bar_network_speed_dual_row_icon",
                    ""
                )
            ) {
                add("") {
                    safeSP.putAny(
                        "status_bar_network_speed_dual_row_icon",
                        ""
                    )
                }
                add("▲▼") {
                    safeSP.putAny("status_bar_network_speed_dual_row_icon", "▲▼")
                }
                add("△▽") {
                    safeSP.putAny("status_bar_network_speed_dual_row_icon", "△▽")
                }
                add("↑↓") {
                    safeSP.putAny("status_bar_network_speed_dual_row_icon", "↑↓")
                }
            })
        Text(
            textId = R.string.status_bar_network_speed_dual_row_size,
            dataBindingRecv = statusBarDualRowNetworkSpeedBinding.binding.getRecv(2)
        )
        SeekBarWithText(
            "status_bar_network_speed_dual_row_size",
            0,
            9,
            0,
            dataBindingRecv = statusBarDualRowNetworkSpeedBinding.binding.getRecv(2)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.hide_slow_speed_network_speed,
            ),
            SwitchV(
                "hide_slow_speed_network_speed",
            )
        )
        SeekBarWithText("slow_speed_degree", 0, 200, 1)
    }
}