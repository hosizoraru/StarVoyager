package star.sky.voyager.hook.hooks.systemui

import android.content.ComponentName
import android.content.Intent
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.hook.views.WeatherView
import star.sky.voyager.utils.api.setObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getBoolean
import star.sky.voyager.utils.key.hasEnable

object OldNotificationWeather : HookRegister() {
    override fun init() = hasEnable("notification_weather") {
        var mWeatherView: TextView? = null
        val isDisplayCity = getBoolean("notification_weather_city", false)

        loadClass("com.android.systemui.qs.MiuiQSHeaderView").methodFinder()
            .filterByName("onFinishInflate")
            .first().createHook {
                after {
                    val viewGroup = it.thisObject as ViewGroup
                    val context = viewGroup.context
                    val layoutParam =
                        loadClass("androidx.constraintlayout.widget.ConstraintLayout\$LayoutParams")
                            .getConstructor(Int::class.java, Int::class.java)
                            .newInstance(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            ) as ViewGroup.MarginLayoutParams

                    layoutParam.setObjectField(
                        "endToStart",
                        context.resources.getIdentifier(
                            "notification_shade_shortcut",
                            "id",
                            context.packageName
                        )
                    )
                    layoutParam.setObjectField(
                        "topToTop",
                        context.resources.getIdentifier(
                            "notification_shade_shortcut",
                            "id",
                            context.packageName
                        )
                    )
                    layoutParam.setObjectField(
                        "bottomToBottom",
                        context.resources.getIdentifier(
                            "notification_shade_shortcut",
                            "id",
                            context.packageName
                        )
                    )

                    mWeatherView = WeatherView(context, isDisplayCity).apply {
                        setTextAppearance(
                            context.resources.getIdentifier(
                                "TextAppearance.StatusBar.Expanded.Clock.QuickSettingDate",
                                "style",
                                context.packageName
                            )
                        )
                        layoutParams = layoutParam
                    }
                    viewGroup.addView(mWeatherView)
                    (mWeatherView as WeatherView).setOnClickListener {
                        try {
                            val intent = Intent().apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                component = ComponentName(
                                    "com.miui.weather2",
                                    "com.miui.weather2.ActivityWeatherMain"
                                )
                            }
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            makeText(context, "启动失败，可能是不支持", LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
    }
}