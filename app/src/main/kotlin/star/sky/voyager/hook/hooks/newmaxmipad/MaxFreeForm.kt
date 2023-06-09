package star.sky.voyager.hook.hooks.newmaxmipad

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XC_MethodHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object MaxFreeForm : HookRegister() {
    override fun init() = hasEnable("max_free_form") {
        when (hostPackageName) {
            "android" -> {
                val displayStrategy =
                    loadClass("com.android.server.wm.MiuiFreeFormStackDisplayStrategy")
                val managerService =
                    loadClass("com.android.server.wm.MiuiFreeFormManagerService")
                displayStrategy.methodFinder()
                    .filterByName("getMaxMiuiFreeFormStackCount")
                    .first().createHook {
                        returnConstant(256)
                    }
                displayStrategy.methodFinder()
                    .filterByName("getMaxMiuiFreeFormStackCountForFlashBack")
                    .first().createHook {
                        returnConstant(256)
                    }
                managerService.methodFinder()
                    .filterByName("shouldStopStartFreeform")
                    .first().createHook {
                        returnConstant(false)
                    }
            }

            "com.miui.home" -> {
                val gestureUtils =
                    loadClass("com.miui.home.launcher.RecentsAndFSGestureUtils")
                val topWindowCrop =
                    loadClass("com.miui.home.recents.views.RecentsTopWindowCrop")
                val miuiMultiWindowUtils =
                    loadClass("android.util.MiuiMultiWindowUtils")
                val miuiFreeFormManager =
                    loadClass("miui.app.MiuiFreeFormManager")
                var hook1: List<XC_MethodHook.Unhook>? = null
                var hook2: List<XC_MethodHook.Unhook>? = null

                gestureUtils.methodFinder()
                    .filterByName("canTaskEnterMiniSmallWindow")
                    .toList().createHooks {
                        returnConstant(true)
                    }
                gestureUtils.methodFinder()
                    .filterByName("canTaskEnterSmallWindow")
                    .toList().createHooks {
                        returnConstant(true)
                    }

                topWindowCrop.methodFinder()
                    .filterByName("startSmallWindow")
                    .toList().createHooks {
                        before {
                            hook1 = miuiMultiWindowUtils.methodFinder()
                                .filterByName("startSmallFreeform")
                                .filterByParamCount(4)
                                .toList().createHooks {
                                    before {
                                        it.args[3] = false
                                        hook2 = miuiFreeFormManager.methodFinder()
                                            .filterByName("getAllFreeFormStackInfosOnDisplay")
                                            .toList().createHooks {
                                                before { param ->
                                                    param.result = null
                                                }
                                            }
                                    }
                                }
                            miuiMultiWindowUtils.methodFinder()
                                .filterByName("startSmallFreeform")
                                .toList().createHooks {
                                    after {
                                        hook2?.forEach {
                                            it.unhook()
                                        }
                                    }
                                }
                        }
                    }
                topWindowCrop.methodFinder()
                    .filterByName("startSmallWindow")
                    .toList().createHooks {
                        after {
                            hook1?.forEach {
                                it.unhook()
                            }
                        }
                    }
            }
        }
    }
}