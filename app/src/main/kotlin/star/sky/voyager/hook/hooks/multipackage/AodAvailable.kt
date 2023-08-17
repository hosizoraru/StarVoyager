package star.sky.voyager.hook.hooks.multipackage

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.voyager.LazyClass.FeatureParserCls

object AodAvailable : HookRegister() {
    override fun init() {
        when (hostPackageName) {
            "android" -> {
                FeatureParserCls.methodFinder()
                    .filterByName("getBoolean")
                    .toList().createHooks {
                        before {
                            when (it.args[0]) {
                                "support_aod", "aod_support_keycode_goto_dismiss" ->
                                    it.result = true
                            }
                        }
                    }
            }

            "com.android.settings" -> {
                val aodUtilsCls =
                    loadClass("com.android.settings.utils.AodUtils")

                aodUtilsCls.methodFinder().filter {
                    name in setOf("isAodAvailable", "actionAvailable", "isAodEnabled")
                }.toList().createHooks {
                    returnConstant(true)
                }

                FeatureParserCls.methodFinder()
                    .filterByName("getBoolean")
                    .toList().createHooks {
                        before {
                            if (it.args[0] == "support_aod") {
                                it.result = true
                            }
                            if (it.args[0] == "aod_support_keycode_goto_dismiss") {
                                it.result = true
                            }
                        }
                    }
            }

            "com.xiaomi.misettings" -> {
                FeatureParserCls.methodFinder()
                    .filterByName("getBoolean")
                    .toList().createHooks {
                        before {
                            when (it.args[0]) {
                                "support_aod", "aod_support_keycode_goto_dismiss" ->
                                    it.result = true
                            }
                        }
                    }
            }

            "com.android.systemui" -> {
                val deviceConfigCls =
                    loadClass("com.miui.systemui.DeviceConfig")

                deviceConfigCls.declaredFields.first { field ->
                    field.name == "SUPPORT_AOD"
                }.apply { isAccessible = true }.set(null, true)

//                deviceConfigCls.declaredFields.toList().forEach {
//                    Log.i("${it.name}: ${it.get(null)}")
//                }

                FeatureParserCls.methodFinder()
                    .filterByName("getBoolean")
                    .toList().createHooks {
                        before {
                            when (it.args[0]) {
                                "support_aod", "aod_support_keycode_goto_dismiss" ->
                                    it.result = true
                            }
                        }
                    }
            }

            "com.miui.aod" -> {
                val utilsCls =
                    loadClass("com.miui.aod.Utils")

                utilsCls.declaredFields.first { field ->
                    field.name == "SUPPORT_AOD"
                }.apply { isAccessible = true }.set(null, true)

                utilsCls.methodFinder()
                    .filterByName("isSupportAodAnimateDevice")
                    .first().createHook {
                        returnConstant(true)
                    }

                FeatureParserCls.methodFinder()
                    .filterByName("getBoolean")
                    .toList().createHooks {
                        before {
                            when (it.args[0]) {
                                "support_aod", "aod_support_keycode_goto_dismiss" ->
                                    it.result = true
                            }
                        }
                    }
            }
        }
    }
}