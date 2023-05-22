package star.sky.voyager.hook.hooks.systemui

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.hook.views.WeatherData
import star.sky.voyager.utils.api.callMethod
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils
import star.sky.voyager.utils.key.hasEnable

@SuppressLint("StaticFieldLeak")
object NewNotificationWeather : HookRegister() {
    // TODO: Android13控制中心天气不可用
    lateinit var weather: WeatherData
    var clockId: Int = -2

    override fun init() = hasEnable("control_center_weather") {
        val isDisplayCity = XSPUtils.getBoolean("notification_weather_city", false)
        val ControlCenterDateViewClass =
            loadClass("com.android.systemui.controlcenter.phone.widget.ControlCenterDateView")
        ControlCenterDateViewClass.methodFinder()
            .findSuper().first {
                name == "onDetachedFromWindow"
            }.createHook {
                before {
                    if ((it.thisObject as TextView).id == clockId && this@NewNotificationWeather::weather.isInitialized) {
                        weather.onDetachedFromWindow()
                    }
                }
            }

        ControlCenterDateViewClass.methodFinder()
            .findSuper().first {
                name == "onDetachedFromWindow"
            }.createHook {
                before {
                    if ((it.thisObject as TextView).id == clockId && this@NewNotificationWeather::weather.isInitialized) {
                        weather.onDetachedFromWindow()
                    }
                }
            }

        ControlCenterDateViewClass.methodFinder()
            .findSuper().first {
                name == "setText"
            }.createHook {
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

        loadClass("com.android.systemui.shared.plugins.PluginManagerImpl").methodFinder().first {
            name == "getClassLoader"
        }.createHook {
            after { getClassLoader ->
                val appInfo = getClassLoader.args[0] as ApplicationInfo
                val classLoader = getClassLoader.result as ClassLoader
                if (appInfo.packageName == "miui.systemui.plugin") {
                    loadClass("miui.systemui.controlcenter.windowview.MainPanelHeaderController").methodFinder()
                        .first {
                            name == "addClockViews"
                        }.createHook {
                            after {
                                val dateView = it.thisObject.getObjectFieldAs<TextView>("dateView")
                                clockId = dateView.id
                                weather = WeatherData(dateView.context, isDisplayCity)
                                weather.callBacks = {
                                    dateView.callMethod("updateTime")
                                }
                            }
                        }
                }
            }
        }
    }
}