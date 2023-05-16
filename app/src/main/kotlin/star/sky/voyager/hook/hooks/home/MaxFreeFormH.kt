package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.MemberExtensions.paramCount
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XC_MethodHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object MaxFreeFormH : HookRegister() {
    override fun init() = hasEnable("max_free_form") {
        loadClass("com.miui.home.launcher.RecentsAndFSGestureUtils").methodFinder().first {
            name == "canTaskEnterMiniSmallWindow"
        }.createHook {
            returnConstant(true)
        }

        loadClass("com.miui.home.launcher.RecentsAndFSGestureUtils").methodFinder().first {
            name == "canTaskEnterSmallWindow"
        }.createHook {
            returnConstant(true)
        }

        var hook1: List<XC_MethodHook.Unhook>?
        var hook2: List<XC_MethodHook.Unhook>? = null
        loadClass("com.miui.home.recents.views.RecentsTopWindowCrop").methodFinder().first {
            name == "startSmallWindow"
        }.createHook {
            before {
                hook1 = listOf(loadClass("android.util.MiuiMultiWindowUtils").methodFinder().first {
                    name == "startSmallFreeform" && paramCount == 4
                }.createHook {
                    before {
                        it.args[3] = false
                        hook2 =
                            listOf(loadClass("miui.app.MiuiFreeFormManager").methodFinder().first {
                                name == "getAllFreeFormStackInfosOnDisplay"
                            }.createHook {
                                before { param ->
                                    param.result = null
                                }
                            })
                    }
                })
                loadClass("android.util.MiuiMultiWindowUtils").methodFinder().first {
                    name == "startSmallFreeform"
                }.createHook {
                    after {
                        hook2?.forEach {
                            it.unhook()
                        }
                    }
                }
                loadClass("com.miui.home.recents.views.RecentsTopWindowCrop").methodFinder().first {
                    name == "startSmallWindow"
                }.createHook {
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