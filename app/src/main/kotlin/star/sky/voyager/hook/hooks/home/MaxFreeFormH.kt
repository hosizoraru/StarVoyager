package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XC_MethodHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object MaxFreeFormH : HookRegister() {
    override fun init() = hasEnable("max_free_form") {
        loadClass("com.miui.home.launcher.RecentsAndFSGestureUtils").methodFinder()
            .filterByName("canTaskEnterMiniSmallWindow")
            .toList().createHooks {
                returnConstant(true)
            }

        loadClass("com.miui.home.launcher.RecentsAndFSGestureUtils").methodFinder()
            .filterByName("canTaskEnterSmallWindow")
            .toList().createHooks {
                returnConstant(true)
            }

        var hook1: List<XC_MethodHook.Unhook>?
        var hook2: List<XC_MethodHook.Unhook>? = null
        loadClass("com.miui.home.recents.views.RecentsTopWindowCrop").methodFinder()
            .filterByName("startSmallWindow")
            .toList().createHooks {
                before {
                    hook1 = loadClass("android.util.MiuiMultiWindowUtils").methodFinder()
                        .filterByName("startSmallFreeform")
                        .filterByParamCount(4)
                        .toList().createHooks {
                            before {
                                it.args[3] = false
                                hook2 = loadClass("miui.app.MiuiFreeFormManager").methodFinder()
                                    .filterByName("getAllFreeFormStackInfosOnDisplay")
                                    .toList().createHooks {
                                        before { param ->
                                            param.result = null
                                        }
                                    }
                            }
                        }
                    loadClass("android.util.MiuiMultiWindowUtils").methodFinder()
                        .filterByName("startSmallFreeform")
                        .toList().createHooks {
                            after {
                                hook2?.forEach {
                                    it.unhook()
                                }
                            }
                        }
                    loadClass("com.miui.home.recents.views.RecentsTopWindowCrop").methodFinder()
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