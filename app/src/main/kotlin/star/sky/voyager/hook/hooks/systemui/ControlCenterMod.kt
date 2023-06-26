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
import star.sky.voyager.utils.voyager.PluginClassLoader.hookPluginClassLoader
import star.sky.voyager.utils.voyager.SetKeyMap.setKeyMap
import star.sky.voyager.utils.voyager.SetKeyMap.whenT
import star.sky.voyager.utils.voyager.SetKeyMap.whenV

object ControlCenterMod : HookRegister() {
    override fun init() = hasEnable("control_center_mod") {
        lateinit var miuiClockView: TextView
        lateinit var miuiDateView: TextView
        lateinit var miuiCarrierView: TextView

        val controlCenterStatusBarClass =
            loadClass("com.android.systemui.controlcenter.phone.widget.ControlCenterStatusBar")

        hookPluginClassLoader { _, classLoader ->
            val mainPanelHeaderController =
                loadClass(
                    "miui.systemui.controlcenter.windowview.MainPanelHeaderController",
                    classLoader
                )

            val updateClocksAppearance =
                mainPanelHeaderController.methodFinder()
                    .filterByName("updateClocksAppearance")
                    .first()

            updateClocksAppearance.createHook {
                before { param ->
                    miuiClockView = param.thisObject.getObjectFieldAs<TextView>("clockView")
                    miuiDateView = param.thisObject.getObjectFieldAs<TextView>("dateView")

                    hasEnable("text_size_def_log") {
                        Log.ix("Control Center Clock:  ${miuiClockView.textSize}")
                        Log.ix("Control Center Date:  ${miuiDateView.textSize}")
                    }
                }
            }

            whenT("control_center_clock", "control_center_date") {
                mainPanelHeaderController.declaredMethods.toList().createHooks {
                    after {
                        setKeyMap(
                            mapOf(
                                "control_center_clock" to {
                                    miuiClockView.visibility = View.GONE
                                },
                                "control_center_date" to {
                                    miuiDateView.visibility = View.GONE
                                }
                            )
                        )
                    }
                }
            }

            updateClocksAppearance.createHook {
                after {
                    whenV("control_center_clock") {
                        false then {
                            setKeyMap(
                                mapOf(
                                    "control_center_clock_font" to {
                                        miuiClockView.typeface = Typeface.DEFAULT
                                    },
                                    "control_center_clock_bold" to {
                                        miuiClockView.typeface = Typeface.DEFAULT_BOLD
                                    },
                                    "control_center_clock_size_custom" to {
                                        val clockSize = getInt(
                                            "control_center_clock_size",
                                            miuiClockView.textSize.toInt()
                                        ).toFloat()

                                        miuiClockView.setTextSize(
                                            TypedValue.COMPLEX_UNIT_SHIFT,
                                            clockSize
                                        )
                                    },
                                    "control_center_clock_color_custom" to {
                                        val clockColor =
                                            parseColor(
                                                getString(
                                                    "control_center_clock_color",
                                                    "#FFFFFF"
                                                )
                                            )
                                        miuiClockView.setTextColor(clockColor)
                                    }
                                )
                            )
                        }
                    }

                    whenV("control_center_date") {
                        false then {
                            setKeyMap(
                                mapOf(
                                    "control_center_date_font" to {
                                        miuiDateView.typeface = Typeface.DEFAULT
                                    },
                                    "control_center_date_bold" to {
                                        miuiDateView.typeface = Typeface.DEFAULT_BOLD
                                    },
                                    "control_center_date_size_custom" to {
                                        val dateSize = getInt(
                                            "control_center_date_size",
                                            miuiDateView.textSize.toInt()
                                        ).toFloat()

                                        miuiDateView.setTextSize(
                                            TypedValue.COMPLEX_UNIT_SHIFT,
                                            dateSize
                                        )
                                    },
                                    "control_center_date_color_custom" to {
                                        val dateColor =
                                            parseColor(
                                                getString(
                                                    "control_center_date_color",
                                                    "#FFFFFF"
                                                )
                                            )
                                        miuiDateView.setTextColor(dateColor)
                                    }
                                )
                            )
                        }
                    }
//                        Log.i("Control Center Clock:  ${miuiClockView.textSize}  ${miuiClockView.typeface}  ${miuiClockView.textColors}")
//                        Log.i("Control Center Date:  ${miuiDateView.textSize}  ${miuiDateView.typeface}  ${miuiDateView.textColors}")
                }
            }
        }

        val carrierCls =
            controlCenterStatusBarClass.methodFinder()
                .filterByName("onDensityOrFontScaleChanged")
                .first()

        carrierCls.createHook {
            before { param ->
                miuiCarrierView =
                    param.thisObject.getObjectFieldAs<TextView>("carrierText")
                hasEnable("text_size_def_log") {
                    Log.ix("Control Center Carrier: ${miuiCarrierView.textSize}")
                }
            }
        }

        whenV("control_center_carrier") {
            true then {
                controlCenterStatusBarClass.declaredMethods.toList().createHooks {
                    after {
                        miuiCarrierView.visibility = View.GONE
                    }
                }
            }

            false then {
                carrierCls.createHook {
                    after {
                        setKeyMap(
                            mapOf(
                                "control_center_carrier_font" to {
                                    miuiCarrierView.typeface = Typeface.DEFAULT
                                },
                                "control_center_carrier_bold" to {
                                    miuiCarrierView.typeface = Typeface.DEFAULT_BOLD
                                },
                                "control_center_carrier_size_custom" to {
                                    val carrierSize = getInt(
                                        "control_center_carrier_size",
                                        miuiCarrierView.textSize.toInt()
                                    ).toFloat()
                                    miuiCarrierView.setTextSize(
                                        TypedValue.COMPLEX_UNIT_SHIFT,
                                        carrierSize
                                    )
                                },
                                "control_center_carrier_color_custom" to {
                                    val carrierColor =
                                        parseColor(
                                            getString(
                                                "control_center_carrier_color",
                                                "#FFFFFF"
                                            )
                                        )
                                    miuiCarrierView.setTextColor(carrierColor)
                                }
                            )
                        )
//                        Log.i("Control Center Carrier: ${miuiCarrierView.textSize}  ${miuiCarrierView.typeface}  ${miuiCarrierView.textColors}")
                    }
                }
            }
        }
    }
}