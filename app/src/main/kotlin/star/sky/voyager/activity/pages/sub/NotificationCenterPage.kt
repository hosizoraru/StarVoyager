package star.sky.voyager.activity.pages.sub

import android.annotation.SuppressLint
import android.view.View
import android.widget.Switch
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R
import star.sky.voyager.utils.voyager.LineB.lineB
import star.sky.voyager.utils.voyager.TabletSeekBar.createTextWithSeekBar
import star.sky.voyager.utils.yife.Build.IS_TABLET

@SuppressLint("NonConstantResourceId")
@BMPage("notification_center", titleId = R.string.notification_center, hideMenu = false)
//@BMPage("notification_center", "Notification Center", hideMenu = false)
class NotificationCenterPage : BasePage() {
    override fun onCreate() {
        TitleText(textId = R.string.notification_center)
        val showWeatherMainSwitchBinding = GetDataBinding({
            safeSP.getBoolean(
                "notification_weather",
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
                textId = R.string.show_weather_main_switch,
                colorId = R.color.blue
            ),
            SwitchV(
                "notification_weather",
                dataBindingSend = showWeatherMainSwitchBinding.bindingSend
            )
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.show_city,
            ),
            SwitchV("notification_weather_city"),
            dataBindingRecv = showWeatherMainSwitchBinding.binding.getRecv(2)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.can_notification_slide,
                tipsId = R.string.can_notification_slide_summary,
            ),
            SwitchV("can_notification_slide"),
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.notification_settings_no_white_list
            ),
            SwitchV("notification_settings_no_white_list", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.notification_channel_setting,
                tipsId = R.string.notification_channel_setting_summary,
            ),
            SwitchV("notification_channel_setting"),
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.notification_icon,
            ),
            SwitchV("notification_icon"),
        )
        Line()
        val ncBinding = GetDataBinding({
            safeSP.getBoolean(
                "notification_mod",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.notification_mod,
                tipsId = R.string.notification_mod_tips
            ),
            SwitchV(
                "notification_mod",
                false,
                dataBindingSend = ncBinding.bindingSend
            )
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.text_size_def_log,
            ), SwitchV("text_size_def_log", false),
            dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        lineB(ncBinding.binding.getRecv(1))
        TitleText(textId = R.string.time, dataBindingRecv = ncBinding.binding.getRecv(1))
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.hide,
            ), SwitchV("notification_time", false),
            dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_font,
            ), SwitchV("notification_time_font", false),
            dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_font_bold,
            ), SwitchV("notification_time_bold", false),
            dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.font_size_custom,
            ), SwitchV("notification_time_size_custom", false),
            dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        createTextWithSeekBar(
            IS_TABLET,
            "notification_time_size",
            Triple(50, 194, 97),
            Triple(60, 200, 130),
            ncBinding.binding
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.color_custom,
            ), SwitchV("notification_time_color_custom", false),
            dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.notification_time_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString("notification_time_color", "#FFFFFF")
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "notification_time_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        lineB(ncBinding.binding.getRecv(1))
        TitleText(textId = R.string.date, dataBindingRecv = ncBinding.binding.getRecv(1))
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.hide,
            ), SwitchV("notification_date", false),
            dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_font,
            ), SwitchV("notification_date_font", false),
            dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_font_bold,
            ), SwitchV("notification_date_bold", false),
            dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.font_size_custom,
            ), SwitchV("notification_date_size_custom", false),
            dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        createTextWithSeekBar(
            IS_TABLET,
            "notification_date_size",
            Triple(10, 62, 31),
            Triple(20, 82, 41),
            ncBinding.binding
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.color_custom,
            ), SwitchV("notification_date_color_custom", false),
            dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.notification_date_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString("notification_date_color", "#FFFFFF")
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "notification_date_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        lineB(ncBinding.binding.getRecv(1))
        TitleText(textId = R.string.land_clock, dataBindingRecv = ncBinding.binding.getRecv(1))
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.hide,
            ), SwitchV("notification_land_clock", false),
            dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_font,
            ), SwitchV("notification_land_clock_font", false),
            dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.system_font_bold,
            ), SwitchV("notification_land_clock_bold", false),
            dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.font_size_custom,
            ), SwitchV("notification_land_clock_size_custom", false),
            dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        createTextWithSeekBar(
            IS_TABLET,
            "notification_land_clock_size",
            Triple(10, 60, 30),
            Triple(10, 74, 37),
            ncBinding.binding
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.color_custom,
            ), SwitchV("notification_land_clock_color_custom", false),
            dataBindingRecv = ncBinding.binding.getRecv(1)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.color,
                onClickListener = {
                    MIUIDialog(activity) {
                        setTitle(R.string.notification_land_clock_color)
                        setEditText(
                            "",
                            "${activity.getString(R.string.current)}${
                                safeSP.getString(
                                    "notification_land_clock_color",
                                    "#FFFFFF"
                                )
                            }"
                        )
                        setLButton(textId = R.string.cancel) {
                            dismiss()
                        }
                        setRButton(textId = R.string.done) {
                            if (getEditText() != "") {
                                safeSP.putAny(
                                    "notification_land_clock_color",
                                    getEditText()
                                )
                            }
                            dismiss()
                        }
                    }.show()
                }), dataBindingRecv = ncBinding.binding.getRecv(1)
        )
    }
}