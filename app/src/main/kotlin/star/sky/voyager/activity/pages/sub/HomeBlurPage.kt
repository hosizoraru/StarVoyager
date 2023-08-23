package star.sky.voyager.activity.pages.sub

import android.view.View
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

//@SuppressLint("NonConstantResourceId")
//@BMPage("home_blur", titleId = R.string.blur, hideMenu = false)
@BMPage("home_blur", "Home Blur", hideMenu = false)
class HomeBlurPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_high_end_device,
                tipsId = R.string.home_high_end_device_summary
            ),
            SwitchV("home_high_end_device", false)
        )
        val blurBindingBlur = GetDataBinding({
            MIUIActivity.safeSP.getBoolean(
                "home_use_complete_blur",
                false
            )
        }) { view, flags, data ->
            if (flags == 1) view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
        }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_use_complete_blur,
                tipsId = R.string.home_use_complete_blur_summary
            ),
            SwitchV("home_use_complete_blur", false, dataBindingSend = blurBindingBlur.bindingSend)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_complete_blur_fix,
                tipsId = R.string.home_complete_blur_fix_summary
            ),
            SwitchV("home_complete_blur_fix", false),
            dataBindingRecv = blurBindingBlur.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.home_blur_wallpaper),
            SwitchV("home_blur_wallpaper")
        )
        Line()
        TitleText(textId = R.string.open_on_demand)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.recent_blur_for_pad6,
                tipsId = R.string.recent_blur_for_pad6_summary
            ),
            SwitchV("recent_blur_for_pad6", false),
            dataBindingRecv = blurBindingBlur.binding.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.home_blur_when_open_folder,
                tipsId = R.string.home_blur_when_open_folder_summary
            ), SwitchV("home_blur_when_open_folder")
        )
        TextSummaryWithSwitch(
            TextSummaryV(textId = R.string.download_animation),
            SwitchV("download_animation")
        )
    }
}