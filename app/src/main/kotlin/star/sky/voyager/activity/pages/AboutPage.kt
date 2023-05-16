package star.sky.voyager.activity.pages

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.BuildConfig
import star.sky.voyager.R
import java.text.SimpleDateFormat
import java.util.Locale

@BMPage("about_module", "About Module", hideMenu = true)
class AboutPage : BasePage() {
    override fun onCreate() {
        ImageWithText(
            authorHead = getDrawable(R.drawable.voyager_icon),
            authorName = getString(R.string.app_name),
            authorTips = "${
                SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss",
                    Locale.getDefault()
                ).format(BuildConfig.BUILD_TIME.toLong())
            }\n${BuildConfig.VERSION_NAME}(${BuildConfig.BUILD_TYPE})",
            onClickListener = {
                try {
                    activity.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://t.me/VoyagerMIUIUpdate")
                        )
                    )
                    Toast.makeText(activity, "♪(･ω･)ﾉ", Toast.LENGTH_LONG)
                        .show()
                } catch (e: Exception) {
                    val uri =
                        Uri.parse("https://github.com/hosizoraru")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    activity.startActivity(intent)
                }
            })
        Line()
        TitleText(textId = R.string.developer)
        ImageWithText(
            authorHead = getDrawable(R.drawable.voyager),
            authorName = "Voyager",
            authorTips = "一位在星海中旅行的旅行者,也叫星空",
            onClickListener = {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/hosizoraru")
                    )
                )
                Toast.makeText(activity, "♪(･ω･)ﾉ", Toast.LENGTH_SHORT).show()
            })
        Line()
        TitleText(text = getString(R.string.discussions))

        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.tg_channel,
                onClickListener = {
                    try {
                        activity.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("tg://resolve?domain=VoyagerMIUIUpdate")
                            )
                        )
                    } catch (e: Exception) {
                        Toast.makeText(activity, "您未安装Telegram系应用", Toast.LENGTH_SHORT)
                            .show()
                        val uri = Uri.parse("https://t.me/VoyagerMIUIUpdate")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        activity.startActivity(intent)
                    }
                })
        )

        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.opensource,
                onClickListener = {
                    try {
                        val uri =
                            Uri.parse("https://github.com/hosizoraru/StarVoyager")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        activity.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(activity, "访问失败", Toast.LENGTH_SHORT).show()
                    }
                })
        )
    }
}