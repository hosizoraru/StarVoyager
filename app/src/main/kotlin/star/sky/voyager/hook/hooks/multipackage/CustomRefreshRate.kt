package star.sky.voyager.hook.hooks.multipackage

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.MemberExtensions.isFinal
import com.github.kyuubiran.ezxhelper.MemberExtensions.isStatic
import com.github.kyuubiran.ezxhelper.ObjectUtils.setObject
import com.github.kyuubiran.ezxhelper.finders.FieldFinder.`-Static`.fieldFinder
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import star.sky.voyager.utils.yife.DexKit.loadDexKit

object CustomRefreshRate : HookRegister() {
    override fun init() = hasEnable("custom_refresh_rate") {
        when (hostPackageName) {
            "com.miui.powerkeeper" -> {
                loadDexKit()
                dexKitBridge.batchFindMethodsUsingStrings {
                    addQuery("qwq0", listOf("custom_mode_switch", "fucSwitch"))
                    matchType = MatchType.FULL
                }.forEach { (_, methods) ->
                    methods.filter { it.isMethod }.map {
                        it.getMethodInstance(safeClassLoader).createHook {
                            before { param ->
                                setObject(param.thisObject, "mIsCustomFpsSwitch", "true")
//                                setObject(param.thisObject, "fucSwitch", true)
//                                val qwq =
//                                    getObjectOrNull(
//                                        param.thisObject,
//                                        "mIsCustomFpsSwitch"
//                                    )
//                                Log.i("hook mIsCustomFpsSwitch success, its:$qwq")
                            }
                        }
                    }
                }
            }

            "com.xiaomi.misettings" -> {
                loadDexKit()
                dexKitBridge.findMethodUsingString {
                    usingString = "btn_preferce_category"
                    matchType = MatchType.FULL
                }.single().getMethodInstance(classLoader).createHook {
                    before {
                        it.args[0] = true
                    }
                }

                dexKitBridge.batchFindClassesUsingStrings {
                    addQuery(
                        "qwq1",
                        listOf("The current device does not support refresh rate adjustment")
                    )
                    matchType = MatchType.FULL
                }.forEach { (_, classes) ->
                    classes.map {
                        it.getClassInstance(classLoader).fieldFinder()
                            .toList().forEach { field ->
                                if (field.isFinal && field.isStatic) {
                                    field.isAccessible = true
                                    field.set(null, true)
                                }
                            }
                    }
                }
            }
        }
    }
}