package star.sky.voyager.activity.pages.sub

import android.annotation.SuppressLint
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R
import star.sky.voyager.utils.yife.Terminal.exec

@SuppressLint("NonConstantResourceId")
@BMPage("home_recent", titleId = R.string.recent, hideMenu = false)
//@BMPage("home_recent", "Home Recent", hideMenu = false)
class HomeRecentPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_real_memory,
                tipsId = R.string.home_real_memory_summary
            ),
            SwitchV("home_real_memory", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.pad_show_memory,
                tipsId = R.string.open_on_demand,
            ),
            SwitchV("pad_show_memory", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.optimize_unlock_anim),
            SwitchV("optimize_unlock_anim")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.pad_gesture_line),
            SwitchV("pad_gesture_line")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.app_quick_switch),
            SwitchV("app_quick_switch")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.fake_nav_bar,
                tipsId = R.string.fake_nav_bar_summary
            ),
            SwitchV("fake_nav_bar")
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_recent_view_wallpaper_darkening,
                tipsId = R.string.home_recent_view_wallpaper_darkening_summary
            ), SwitchV("home_recent_view_wallpaper_darkening", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_recent_view_remove_card_animation,
                tipsId = R.string.home_recent_view_remove_card_animation_summary
            ), SwitchV("home_recent_view_remove_card_animation", false)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.recent_hide_gesture_line,
            ) {
                exec("settings put global hide_gesture_line 1")
                makeText(
                    activity,
                    getString(R.string.finished),
                    LENGTH_SHORT
                ).show()
            }
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.recent_show_gesture_line,
            ) {
                exec("settings put global hide_gesture_line 0")
                makeText(
                    activity,
                    getString(R.string.finished),
                    LENGTH_SHORT
                ).show()
            }
        )
    }
}