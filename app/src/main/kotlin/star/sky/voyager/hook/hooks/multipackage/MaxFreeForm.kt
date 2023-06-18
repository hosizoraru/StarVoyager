package star.sky.voyager.hook.hooks.multipackage

import android.util.ArraySet
import com.github.kyuubiran.ezxhelper.ClassUtils.invokeStaticMethodBestMatch
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullAs
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object MaxFreeForm : HookRegister() {
    override fun init() = hasEnable("max_free_form") {
        when (hostPackageName) {
            "android" -> {
                val clazzMiuiFreeFormStackDisplayStrategy =
                    loadClass("com.android.server.wm.MiuiFreeFormStackDisplayStrategy")
                clazzMiuiFreeFormStackDisplayStrategy.methodFinder().filter {
                    name in setOf(
                        "getMaxMiuiFreeFormStackCount",
                        "getMaxMiuiFreeFormStackCountForFlashBack"
                    )
                }.toList().createHooks {
                    returnConstant(256)
                }
                clazzMiuiFreeFormStackDisplayStrategy.methodFinder()
                    .filterByName("shouldStopStartFreeform").first()
                    .createHook {
                        returnConstant(false)
                    }
            }

            "com.miui.home" -> {
                val clazzRecentsAndFSGestureUtils =
                    loadClass("com.miui.home.launcher.RecentsAndFSGestureUtils")
                clazzRecentsAndFSGestureUtils.methodFinder().filterByName("canTaskEnterSmallWindow")
                    .toList().createHooks {
                        returnConstant(true)
                    }
                clazzRecentsAndFSGestureUtils.methodFinder()
                    .filterByName("canTaskEnterMiniSmallWindow").toList().createHooks {
                        before {
                            it.result = invokeStaticMethodBestMatch(
                                loadClass("com.miui.home.smallwindow.SmallWindowStateHelper"),
                                "getInstance"
                            )!!.objectHelper()
                                .invokeMethodBestMatch("canEnterMiniSmallWindow") as Boolean
                        }
                    }
                loadClass("com.miui.home.smallwindow.SmallWindowStateHelperUseManager").methodFinder()
                    .filterByName("canEnterMiniSmallWindow").first().createHook {
                        before {
                            it.result = getObjectOrNullAs<ArraySet<*>>(
                                it.thisObject,
                                "mMiniSmallWindowInfoSet"
                            )!!.isEmpty()
                        }
                    }
                loadClass("miui.app.MiuiFreeFormManager").methodFinder()
                    .filterByName("getAllFreeFormStackInfosOnDisplay")
                    .toList().createHooks {
                        before { param ->
                            if (Throwable().stackTrace.any {
                                    it.className == "android.util.MiuiMultiWindowUtils" && it.methodName == "startSmallFreeform"
                                }) {
                                param.result = null
                            }
                        }
                    }
                loadClass("android.util.MiuiMultiWindowUtils").methodFinder()
                    .filterByName("hasSmallFreeform").toList().createHooks {
                        before { param ->
                            if (Throwable().stackTrace.any {
                                    it.className == "android.util.MiuiMultiWindowUtils" && it.methodName == "startSmallFreeform"
                                }) {
                                param.result = false
                            }
                        }
                    }
            }
        }
    }
}