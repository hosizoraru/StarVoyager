package star.sky.voyager.hook.hooks.systemui

import android.graphics.Color.parseColor
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.SetKeyMap.setKeyMap
import star.sky.voyager.utils.voyager.SetKeyMap.whenT
import star.sky.voyager.utils.voyager.SetKeyMap.whenV

object NotificationMod : HookRegister() {
    override fun init() = hasEnable("notification_mod") {
        lateinit var miuiBigTime: TextView
        lateinit var miuiDate: TextView
        lateinit var miuiLandClock: TextView

        val notificationClass =
            loadClass("com.android.systemui.qs.MiuiNotificationHeaderView")

        val updateResource =
            notificationClass.methodFinder()
                .filterByName("updateResources")
                .first()

        updateResource.createHook {
            before { param ->
                miuiBigTime = param.thisObject.getObjectFieldAs("mBigTime")
                miuiDate = param.thisObject.getObjectFieldAs("mDateView")
                miuiLandClock = param.thisObject.getObjectFieldAs("mLandClock")

                hasEnable("text_size_def_log") {
                    Log.ix("Notification time: ${miuiBigTime.textSize}")
                    Log.ix("Notification date: ${miuiDate.textSize}")
                    Log.ix("Notification land clock: ${miuiLandClock.textSize}")
                }
            }
        }

        whenT("notification_time", "notification_date", "notification_land_clock") {
            notificationClass.declaredMethods.toList().createHooks {
                after {
                    setKeyMap(
                        mapOf(
                            "notification_time" to { miuiBigTime.visibility = View.GONE },
                            "notification_date" to { miuiDate.visibility = View.GONE },
                            "notification_land_clock" to {
                                miuiLandClock.visibility = View.GONE
                            }
                        )
                    )
                }
            }
        }

        updateResource.createHook {
            after {
                whenV("notification_time") {
                    false then {
                        setKeyMap(
                            mapOf(
                                "notification_time_font" to {
                                    miuiBigTime.typeface = Typeface.DEFAULT
                                },
                                "notification_time_bold" to {
                                    miuiBigTime.typeface = Typeface.DEFAULT_BOLD
                                },
                                "notification_time_size_custom" to {
                                    val timeSize =
                                        getInt(
                                            "notification_time_size",
                                            miuiBigTime.textSize.toInt()
                                        )
                                            .toFloat()
                                    miuiBigTime.setTextSize(TypedValue.COMPLEX_UNIT_SHIFT, timeSize)
                                },
                                "notification_time_color_custom" to {
                                    val timeColor =
                                        parseColor(getString("notification_time_color", "#FFFFFF"))
                                    miuiBigTime.setTextColor(timeColor)
                                }
                            )
                        )
                    }
                }

                whenV("notification_date") {
                    false then {
                        setKeyMap(
                            mapOf(
                                "notification_date_font" to {
                                    miuiDate.typeface = Typeface.DEFAULT
                                },
                                "notification_date_bold" to {
                                    miuiDate.typeface = Typeface.DEFAULT_BOLD
                                },
                                "notification_date_size_custom" to {
                                    val dateSize =
                                        getInt(
                                            "notification_date_size",
                                            miuiDate.textSize.toInt()
                                        ).toFloat()
                                    miuiDate.setTextSize(TypedValue.COMPLEX_UNIT_SHIFT, dateSize)
                                },
                                "notification_date_color_custom" to {
                                    val dateColor =
                                        parseColor(getString("notification_date_color", "#FFFFFF"))
                                    miuiDate.setTextColor(dateColor)
                                }
                            )
                        )
                    }
                }

                whenV("notification_land_clock") {
                    false then {
                        setKeyMap(
                            mapOf(
                                "notification_land_clock_font" to {
                                    miuiLandClock.typeface = Typeface.DEFAULT
                                },
                                "notification_land_clock_bold" to {
                                    miuiLandClock.typeface = Typeface.DEFAULT_BOLD
                                },
                                "notification_land_clock_size_custom" to {
                                    val clockSize =
                                        getInt(
                                            "notification_land_clock_size",
                                            miuiLandClock.textSize.toInt()
                                        ).toFloat()
                                    miuiLandClock.setTextSize(
                                        TypedValue.COMPLEX_UNIT_SHIFT,
                                        clockSize
                                    )
                                },
                                "notification_land_clock_color_custom" to {
                                    val clockColor =
                                        parseColor(
                                            getString(
                                                "notification_land_clock_color",
                                                "#FFFFFF"
                                            )
                                        )
                                    miuiLandClock.setTextColor(clockColor)
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}

//                    Log.i("miuiBigTime:  ${miuiBigTime.textSize}  ${miuiBigTime.typeface}  ${miuiBigTime.textColors}")
//                    Log.i("miuiDateTime:  ${miuiDate.textSize}  ${miuiDate.typeface}  ${miuiDate.textColors}")
//                    Log.i("miuiLandClock:  ${miuiLandClock.textSize}  ${miuiLandClock.typeface}  ${miuiLandClock.textColors}")