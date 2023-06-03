package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XC_MethodHook
import star.sky.voyager.utils.api.setObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object AlwaysShowMiuiWidget : HookRegister() {
    override fun init() = hasEnable("Show_MIUI_Widget") {
        var hook1: XC_MethodHook.Unhook? = null
        var hook2: XC_MethodHook.Unhook? = null
        try {
            loadClass("com.miui.home.launcher.widget.WidgetsVerticalAdapter").methodFinder()
                .filterByName("buildAppWidgetsItems").first()
        } catch (e: Exception) {
            loadClass("com.miui.home.launcher.widget.BaseWidgetsVerticalAdapter").methodFinder()
                .filterByName("buildAppWidgetsItems").first()
        }.createHook {
            before {
                hook1 = loadClass("com.miui.home.launcher.widget.MIUIAppWidgetInfo").methodFinder()
                    .filterByName("initMiuiAttribute")
                    .filterByParamCount(1)
                    .first().createHook {
                        after {
                            it.thisObject.setObjectField("isMIUIWidget", false)
                        }
                    }
                hook2 = loadClass("com.miui.home.launcher.MIUIWidgetUtil").methodFinder()
                    .filterByName("isMIUIWidgetSupport")
                    .first().createHook {
                        after {
                            it.result = false
                        }
                    }
            }
            after {
                hook1?.unhook()
                hook2?.unhook()
            }
        }
    }
}