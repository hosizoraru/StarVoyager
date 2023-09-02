package star.sky.voyager.hook.hooks.home

import android.appwidget.AppWidgetProviderInfo
import com.github.kyuubiran.ezxhelper.ClassLoaderProvider.classLoader
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object ResizableWidgets : HookRegister() {
    override fun init() = hasEnable("resizable_widgets") {
        val qwq1 =
            loadClass("android.appwidget.AppWidgetHostView", null).methodFinder()
                .filterByName("getAppWidgetInfo").toList()
        val qwq2 =
            dexKitBridge.findMethod {
                methodName = "getAppWidgetInfo"
//                methodReturnType = "android.appwidget.AppWidgetProviderInfo"
            }.map { it.getMethodInstance(classLoader) }.toList()

        setOf(qwq1, qwq2).forEach { methods1 ->
            methods1.filter {
                it.returnType == AppWidgetProviderInfo::class.java
            }.forEach { methods2 ->
                methods2.createHook {
                    after { param ->
                        val widgetInfo =
                            param.result as AppWidgetProviderInfo
                        widgetInfo.resizeMode =
                            AppWidgetProviderInfo.RESIZE_VERTICAL or AppWidgetProviderInfo.RESIZE_HORIZONTAL
                        widgetInfo.minHeight = 0
                        widgetInfo.minWidth = 0
                        widgetInfo.minResizeHeight = 0
                        widgetInfo.minResizeWidth = 0
                        param.result = widgetInfo
                    }
                }
            }
        }
    }
}
//                    Log.i("before: " + widgetInfo.toString() + " " + widgetInfo.minHeight + " " + widgetInfo.minWidth + " " + widgetInfo.minResizeHeight + " " + widgetInfo.minResizeWidth)
//                    Log.i("after: " + widgetInfo.toString() + " " + widgetInfo.minHeight + " " + widgetInfo.minWidth + " " + widgetInfo.minResizeHeight + " " + widgetInfo.minResizeWidth)