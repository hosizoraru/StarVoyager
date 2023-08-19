package star.sky.voyager.activity.pages.sub

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.R

@SuppressLint("NonConstantResourceId")
@BMPage("front_camera_assistant", titleId = R.string.front_camera_assistant, hideMenu = false)
class FrontCameraAssistantPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.vcb_ability
            ),
            SwitchV("vcb_ability", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.privacy_camera
            ),
            SwitchV("privacy_camera", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.camera_face_tracker
            ),
            SwitchV("camera_face_tracker", false)
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.front_camera_assistant,
                onClickListener = {
                    try {
                        val intent = Intent()
                        val comp = ComponentName(
                            "com.miui.securitycenter",
                            "com.miui.gamebooster.beauty.BeautySettingsActivity"
                        )
                        intent.component = comp
                        activity.startActivity(intent)
                    } catch (e: Exception) {
                        makeText(activity, "启动失败，可能是不支持", LENGTH_LONG).show()
                    }
                })
        )
    }
}