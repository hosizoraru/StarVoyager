package star.sky.voyager.hook.hooks.multipackage

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.MemberExtensions.isFinal
import com.github.kyuubiran.ezxhelper.MemberExtensions.isStatic
import com.github.kyuubiran.ezxhelper.ObjectUtils.setObject
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object CustomRefreshRate : HookRegister() {
    override fun init() = hasEnable("custom_refresh_rate") {
        when (hostPackageName) {
            "com.miui.powerkeeper" -> {
                dexKitBridge.batchFindMethodsUsingStrings {
                    addQuery("qwq0", setOf("custom_mode_switch", "fucSwitch"))
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
                val refreshRateActivity =
                    loadClass("com.xiaomi.misettings.display.RefreshRate.RefreshRateActivity")

                dexKitBridge.findMethodUsingString {
                    usingString = "btn_preferce_category"
                    matchType = MatchType.FULL
                }.single().getMethodInstance(classLoader).createHook {
                    before {
                        it.args[0] = true
                    }
                }

                refreshRateActivity.declaredFields.first { field ->
                    field.isFinal && field.isStatic
                }.apply { isAccessible = true }.set(null, true)

//                refreshRateActivity.declaredFields
//                    .toList().forEach { field ->
//                        if (field.isStatic && field.isFinal) {
//                            field.isAccessible = true
//                            field.set(null, true)
//                        }
//                    }
//
//                refreshRateActivity.fieldFinder()
//                    .filterStatic().filterFinal()
//                    .first().apply { isAccessible = true }
//                    .set(null, true)
            }
        }
    }
}
