package star.sky.voyager.activity.pages.main

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri.parse
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.TextSummaryV
import star.sky.voyager.BuildConfig.BUILD_TIME
import star.sky.voyager.BuildConfig.BUILD_TYPE
import star.sky.voyager.BuildConfig.VERSION_NAME
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
                ).format(BUILD_TIME.toLong())
            }\n${VERSION_NAME}(${BUILD_TYPE})",
            onClickListener = {
                try {
                    activity.startActivity(
                        Intent(
                            ACTION_VIEW,
                            parse("https://t.me/VoyagerMIUIUpdate")
                        )
                    )
                    makeText(activity, "♪(･ω･)ﾉ", LENGTH_LONG)
                        .show()
                } catch (e: Exception) {
                    val uri =
                        parse("https://github.com/hosizoraru")
                    val intent = Intent(ACTION_VIEW, uri)
                    activity.startActivity(intent)
                }
            })
        Line()
        TitleText(textId = R.string.developer)
        ImageWithText(
            authorHead = getDrawable(R.drawable.voyager),
            authorName = "Voyager",
            authorTips = getString(R.string.dev_desc),
            onClickListener = {
                activity.startActivity(
                    Intent(
                        ACTION_VIEW,
                        parse("https://github.com/hosizoraru")
                    )
                )
                makeText(activity, "♪(･ω･)ﾉ", LENGTH_SHORT).show()
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
                                ACTION_VIEW,
                                parse("tg://resolve?domain=VoyagerMIUIUpdate")
                            )
                        )
                    } catch (e: Exception) {
                        makeText(activity, R.string.tg_app, LENGTH_SHORT)
                            .show()
                        val uri = parse("tg://resolve?domain=VoyagerMIUIUpdate")
                        val intent = Intent(ACTION_VIEW, uri)
                        activity.startActivity(intent)
                    }
                })
        )

        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.tg_group,
                onClickListener = {
                    try {
                        activity.startActivity(
                            Intent(
                                ACTION_VIEW,
                                parse("https://t.me/+xtTB-ijLrlY2ZjFl")
                            )
                        )
                    } catch (e: Exception) {
                        makeText(activity, R.string.tg_app, LENGTH_SHORT)
                            .show()
                        val uri = parse("https://t.me/+xtTB-ijLrlY2ZjFl")
                        val intent = Intent(ACTION_VIEW, uri)
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
                            parse("https://github.com/hosizoraru/StarVoyager")
                        val intent = Intent(ACTION_VIEW, uri)
                        activity.startActivity(intent)
                    } catch (e: Exception) {
                        makeText(activity, R.string.fail, LENGTH_SHORT).show()
                    }
                })
        )
    }
}