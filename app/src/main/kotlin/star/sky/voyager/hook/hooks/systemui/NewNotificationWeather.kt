package star.sky.voyager.hook.hooks.systemui

import android.annotation.SuppressLint
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullAs
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethod
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.hook.views.WeatherData
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getBoolean
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.PluginClassLoader.hookPluginClassLoader

@SuppressLint("StaticFieldLeak")
object NewNotificationWeather : HookRegister() {
    // TODO: Android13控制中心天气不可用
    private lateinit var weather: WeatherData
    private var clockId: Int = -2

    @SuppressLint("DiscouragedApi", "ClickableViewAccessibility")
    override fun init() = hasEnable("control_center_weather") {
        val isDisplayCity = getBoolean("notification_weather_city", false)
        val controlCenterDateViewClass =
            loadClass("com.android.systemui.controlcenter.phone.widget.QSControlCenterHeaderView")
        controlCenterDateViewClass.methodFinder().findSuper()
            .filterByName("onDetachedFromWindow")
            .first().createHook {
                before {
                    if ((it.thisObject as TextView).id == clockId && this@NewNotificationWeather::weather.isInitialized) {
                        weather.onDetachedFromWindow()
                    }
                }
            }

        controlCenterDateViewClass.methodFinder().findSuper()
            .filterByName("setText")
            .first().createHook {
                before {
                    val time = it.args[0]?.toString()
                    val view = it.thisObject as TextView
                    if (view.id == clockId && time != null && this@NewNotificationWeather::weather.isInitialized) {
//                val layout = view.layoutParams as ViewGroup.MarginLayoutParams
//                val y = view.height / 2
//                layout.topMargin = -y
                        it.args[0] = "${weather.weatherData}$time"
                    }
                }
            }

        hookPluginClassLoader { appInfo, classLoader ->
            loadClass(
                "miui.systemui.controlcenter.windowview.MainPanelHeaderController",
                classLoader
            ).methodFinder()
                .filterByName("addClockViews")
                .first().createHook {
                    after {
                        val dateView = getObjectOrNullAs<TextView>(it.thisObject, "dateView")!!
                        clockId = dateView.id
                        weather = WeatherData(dateView.context, isDisplayCity)
                        weather.callBacks = {
//                            dateView.callMethod("updateTime")
                            invokeMethod(dateView, "updateDate")
                        }
                    }
                }
        }
    }
}