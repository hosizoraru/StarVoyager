package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.voyager.SetKeyMap.setKeyMap
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object GameTurbo : HookRegister() {
    private val GameBoosterFeatureUtilsCls by lazy {
        dexKitBridge.findClass {
            matcher {
                addUsingStringsEquals("GameBoosterFeatureUtils")
            }
        }.first().getInstance(classLoader)
    }

    private val G by lazy {
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals("support_network_rps_mode")
                declaredClass = GameBoosterFeatureUtilsCls.name
            }
        }.first().getMethodInstance(classLoader)
    }

    private val h0 by lazy {
        dexKitBridge.findMethod {
            matcher {
                declaredClass = GameBoosterFeatureUtilsCls.name
                addUsingStringsEquals("grus")
            }
        }.first { it.isMethod && it.getMethodInstance(classLoader) != G }
            .getMethodInstance(classLoader)
    }

    private val k by lazy {
        dexKitBridge.findMethod {
            matcher {
                declaredClass = GameBoosterFeatureUtilsCls.name
                addUsingStringsEquals("cepheus")
            }
        }.first {
            it.isMethod &&
                    it.getMethodInstance(classLoader) != G &&
                    it.getMethodInstance(classLoader) != h0
        }.getMethodInstance(classLoader)
    }

    private val F by lazy {
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals("support_wifi_low_latency_mode")
                declaredClass = GameBoosterFeatureUtilsCls.name
            }
        }.first().getMethodInstance(classLoader)
    }

    private val PQ by lazy {
        dexKitBridge.findMethod {
            matcher {
                declaredClass = GameBoosterFeatureUtilsCls.name
                addUsingStringsEquals("support_touchfeature_gamemode")
            }
        }.map { it.getMethodInstance(classLoader) }.toList()
    }

    private val l by lazy {
        dexKitBridge.findMethod {
            matcher {
                declaredClass = GameBoosterFeatureUtilsCls.name
                addUsingStringsEquals("com.xiaomi.aiasst.vision")
            }
        }.first().getMethodInstance(classLoader)
    }

    private val M by lazy {
        dexKitBridge.findMethod {
            matcher {
                declaredClass = GameBoosterFeatureUtilsCls.name
                addUsingStringsEquals("ro.vendor.fps.switch.default")
            }
        }.first().getMethodInstance(classLoader)
    }

    private val i by lazy {
        dexKitBridge.findMethod {
            matcher {
                declaredClass = GameBoosterFeatureUtilsCls.name
                addUsingStringsEquals("isEnhancedDsdaSupported")
            }
        }.first().getMethodInstance(classLoader)
    }

    override fun init() {
        setKeyMap(
            mapOf(
                "audio_optimization" to {
                    k.createHook {
                        returnConstant(true)
                    }
                },

                "wifi_low_latency_mode" to {
                    F.createHook {
                        returnConstant(true)
                    }
                },

                "touchfeature_gamemode" to {
                    PQ.toList().createHooks {
                        returnConstant(true)
                    }
                },

                "ai_subtitles_videomode" to {
                    l.createHook {
                        returnConstant(true)
                    }
                },

                "fps_switch" to {
                    M.createHook {
                        returnConstant(true)
                    }
                },

                "enhanced_dsda" to {
                    i.createHook {
                        returnConstant(true)
                    }
                },
            )
        )
    }
}