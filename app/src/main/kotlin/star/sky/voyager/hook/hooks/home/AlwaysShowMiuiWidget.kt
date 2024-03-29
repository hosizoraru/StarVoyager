package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object AlwaysShowMiuiWidget : HookRegister() {
    override fun init() = hasEnable("Show_MIUI_Widget") {
        loadClass("com.miui.home.launcher.MIUIWidgetUtil").methodFinder()
            .filterByName("isMIUIWidgetSupport")
            .first().createHook {
                after { param ->
                    if (Throwable().stackTrace.any {
                            it.className in setOf(
                                "com.miui.home.launcher.widget.WidgetsVerticalAdapter",
                                "com.miui.home.launcher.widget.BaseWidgetsVerticalAdapter"
                            )
                        }) {
                        param.result = false
                    }
                }
            }
    }
}

//var hook1: XC_MethodHook.Unhook? = null
//var hook2: XC_MethodHook.Unhook? = null
//val classNames = listOf(
//    "com.miui.home.launcher.widget.WidgetsVerticalAdapter",
//    "com.miui.home.launcher.widget.BaseWidgetsVerticalAdapter"
//)
//
//classNames.forEach { className ->
//    val result = runCatching {
//        loadClass(className).methodFinder()
//            .filterByName("buildAppWidgetsItems")
//            .first()
//            .createHook {
//                before {
//                    hook1 =
//                        loadClass("com.miui.home.launcher.widget.MIUIAppWidgetInfo").methodFinder()
//                            .filterByName("initMiuiAttribute")
//                            .filterByParamCount(1)
//                            .first().createHook {
//                                after {
//                                    it.thisObject.setObjectField("isMIUIWidget", false)
//                                }
//                            }
//                    hook2 =
//                        loadClass("com.miui.home.launcher.MIUIWidgetUtil").methodFinder()
//                            .filterByName("isMIUIWidgetSupport")
//                            .first().createHook {
//                                after {
//                                    it.result = false
//                                }
//                            }
//                }
//                after {
//                    hook1?.unhook()
//                    hook2?.unhook()
//                }
//            }
//    }
//    if (result.isSuccess) return@forEach
//}