package star.sky.voyager.hook.hooks.home

import android.appwidget.AppWidgetProviderInfo
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object ResizableWidgets : HookRegister() {
    override fun init() = hasEnable("resizable_widgets") {
        loadClass("android.appwidget.AppWidgetHostView", null).methodFinder()
            .filterByName("getAppWidgetInfo").toList().createHooks {
                after { param ->
                    val widgetInfo = param.result as AppWidgetProviderInfo
                    Log.i("before: " + widgetInfo.toString() + " " + widgetInfo.minHeight + " " + widgetInfo.minWidth + " " + widgetInfo.minResizeHeight + " " + widgetInfo.minResizeWidth)
                    widgetInfo.resizeMode =
                        AppWidgetProviderInfo.RESIZE_VERTICAL or AppWidgetProviderInfo.RESIZE_HORIZONTAL
                    widgetInfo.minHeight = 0
                    widgetInfo.minWidth = 0
                    widgetInfo.minResizeHeight = 0
                    widgetInfo.minResizeWidth = 0
                    param.result = widgetInfo
                    Log.i("after: " + widgetInfo.toString() + " " + widgetInfo.minHeight + " " + widgetInfo.minWidth + " " + widgetInfo.minResizeHeight + " " + widgetInfo.minResizeWidth)
                }
            }
    }
}