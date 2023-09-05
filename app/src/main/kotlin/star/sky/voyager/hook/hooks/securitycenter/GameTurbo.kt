package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import org.luckypray.dexkit.query.enums.StringMatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.voyager.SetKeyMap.setKeyMap
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object GameTurbo : HookRegister() {
    private val GameBoosterFeatureUtilsCls by lazy {
//        dexKitBridge.batchFindClassesUsingStrings {
//            addQuery("qwq1", setOf("GameBoosterFeatureUtils"))
//            matchType = MatchType.FULL
//        }.firstNotNullOf { (_, classes1) -> classes1.firstOrNull() }
        dexKitBridge.findClass {
            matcher {
                usingStrings = listOf("GameBoosterFeatureUtils")
                StringMatchType.Equals
            }
        }.first().getInstance(classLoader)
    }

    private val G by lazy {
//        dexKitBridge.findMethodUsingString {
//            methodDeclareClass = GameBoosterFeatureUtilsCls.name
//            usingString = "support_network_rps_mode"
//            matchType = MatchType.FULL
//        }.single().getMethodInstance(classLoader)
        dexKitBridge.findMethod {
            matcher {
                usingStrings = listOf("support_network_rps_mode")
                declaredClass = GameBoosterFeatureUtilsCls.name
                StringMatchType.Equals
            }
        }.first().getMethodInstance(classLoader)
    }

    private val h0 by lazy {
//        dexKitBridge.findMethodUsingString {
//            methodDeclareClass = GameBoosterFeatureUtilsCls.name
//            usingString = "grus"
//            matchType = MatchType.FULL
//        }.single { it.isMethod && it.getMethodInstance(classLoader) != G }
//            .getMethodInstance(classLoader)
        dexKitBridge.findMethod {
            matcher {
                usingStrings = listOf("grus")
                declaredClass = GameBoosterFeatureUtilsCls.name
                StringMatchType.Equals
            }
        }.first { it.isMethod && it.getMethodInstance(classLoader) != G }
            .getMethodInstance(classLoader)
    }

    private val k by lazy {
//        dexKitBridge.findMethodUsingString {
//            methodDeclareClass = GameBoosterFeatureUtilsCls.name
//            usingString = "cepheus"
//            matchType = MatchType.FULL
//        }.single {
//            it.isMethod &&
//                    it.getMethodInstance(classLoader) != G &&
//                    it.getMethodInstance(classLoader) != h0
//        }.getMethodInstance(classLoader)
        dexKitBridge.findMethod {
            matcher {
                usingStrings = listOf("cepheus")
                declaredClass = GameBoosterFeatureUtilsCls.name
                StringMatchType.Equals
            }
        }.first {
            it.isMethod &&
                    it.getMethodInstance(classLoader) != G &&
                    it.getMethodInstance(classLoader) != h0
        }.getMethodInstance(classLoader)
    }

    private val F by lazy {
//        dexKitBridge.findMethodUsingString {
//            methodDeclareClass = GameBoosterFeatureUtilsCls.name
//            usingString = "support_wifi_low_latency_mode"
//            matchType = MatchType.FULL
//        }.single().getMethodInstance(classLoader)
        dexKitBridge.findMethod {
            matcher {
                usingStrings = listOf("support_wifi_low_latency_mode")
                declaredClass = GameBoosterFeatureUtilsCls.name
                StringMatchType.Equals
            }
        }.first().getMethodInstance(classLoader)
    }

    private val PQ by lazy {
//        dexKitBridge.findMethodUsingString {
//            methodDeclareClass = GameBoosterFeatureUtilsCls.name
//            usingString = "support_touchfeature_gamemode"
//            matchType = MatchType.FULL
//        }.map { it.getMethodInstance(classLoader) }
        dexKitBridge.findMethod {
            matcher {
                declaredClass = GameBoosterFeatureUtilsCls.name
                usingStrings = listOf("support_touchfeature_gamemode")
                StringMatchType.Equals
            }
        }.map { it.getMethodInstance(classLoader) }.toList()
    }

    private val l by lazy {
//        dexKitBridge.findMethodUsingString {
//            methodDeclareClass = GameBoosterFeatureUtilsCls.name
//            usingString = "com.xiaomi.aiasst.vision"
//            matchType = MatchType.FULL
//        }.single().getMethodInstance(classLoader)
        dexKitBridge.findMethod {
            matcher {
                declaredClass = GameBoosterFeatureUtilsCls.name
                usingStrings = listOf("com.xiaomi.aiasst.vision")
                StringMatchType.Equals
            }
        }.first().getMethodInstance(classLoader)
    }

    private val M by lazy {
//        dexKitBridge.findMethodUsingString {
//            methodDeclareClass = GameBoosterFeatureUtilsCls.name
//            usingString = "ro.vendor.fps.switch.default"
//            matchType = MatchType.FULL
//        }.single().getMethodInstance(classLoader)
        dexKitBridge.findMethod {
            matcher {
                declaredClass = GameBoosterFeatureUtilsCls.name
                usingStrings = listOf("ro.vendor.fps.switch.default")
                StringMatchType.Equals
            }
        }.first().getMethodInstance(classLoader)
    }

    private val i by lazy {
//        dexKitBridge.findMethodUsingString {
//            methodDeclareClass = GameBoosterFeatureUtilsCls.name
//            usingString = "isEnhancedDsdaSupported"
//            matchType = MatchType.FULL
//        }.single().getMethodInstance(classLoader)
        dexKitBridge.findMethod {
            matcher {
                declaredClass = GameBoosterFeatureUtilsCls.name
                usingStrings = listOf("isEnhancedDsdaSupported")
                StringMatchType.Equals
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

//        dexKitBridge.findMethodUsingString {
//            methodDeclareClass = GameBoosterFeatureUtilsCls.name
//            usingString = "grus"
//            matchType = MatchType.FULL
//        }.filter { it.isMethod }.forEach {
//            val methods1 =
//                it.getMethodInstance(classLoader)
//            if (methods1 != G) h0 = methods1
//        }

//        dexKitBridge.findMethodUsingString {
//            methodDeclareClass = GameBoosterFeatureUtilsCls.name
//            usingString = "cepheus"
//            matchType = MatchType.FULL
//        }.filter { it.isMethod }.forEach {
//            val methods2 =
//                it.getMethodInstance(classLoader)
//            if (methods2 != G && methods2 != h0) k = methods2
//        }

//Log.i("Cls: $GameBoosterFeatureUtilsCls")
//Log.i("G: $G")
//Log.i("h0: $h0")
//Log.i("k: $k")
//Log.i("F: $F")
//Log.i("l: $l")
//Log.i("M: $M")
//Log.i("i: $i")
//Log.i("PQ: $PQ")
//Log.i("Gh0: $Gh0")
//Log.i("Gh0k: $Gh0k")

//    private val Gh0 by lazy {
//        dexKitBridge.findMethodUsingString {
//            methodDeclareClass = GameBoosterFeatureUtilsCls.name
//            usingString = "grus"
//            matchType = MatchType.FULL
//        }.filter { it.isMethod }.map { it.getMethodInstance(classLoader) }
//    }
//
//    private val Gh0k by lazy {
//        dexKitBridge.findMethodUsingString {
//            methodDeclareClass = GameBoosterFeatureUtilsCls.name
//            usingString = "cepheus"
//            matchType = MatchType.FULL
//        }.filter { it.isMethod }.map { it.getMethodInstance(classLoader) }
//    }