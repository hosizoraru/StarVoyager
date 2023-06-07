package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.callMethod
import star.sky.voyager.utils.api.callMethodAs
import star.sky.voyager.utils.api.getObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object AllowMoveAllWidgetToMinus : HookRegister() {
    override fun init() = hasEnable("home_widget_to_minus") {
        loadClass("com.miui.home.launcher.widget.MIUIWidgetHelper").methodFinder()
            .filterByName("canDragToPa")
            .filterByParamCount(2)
            .first().createHook {
                before { param ->
                    runCatching {
                        val dragInfo = param.args[1].callMethod("getDragInfo")!!
//                        val spanX = getObjectOrNullUntilSuperclassAs<Int>(
//                            dragInfo,
//                            "spanX"
//                        )!!
//                        val spanY = getObjectOrNullUntilSuperclassAs<Int>(
//                            dragInfo,
//                            "spanY"
//                        )!!
                        val spanX = dragInfo.getObjectField("spanX")!!
                        val spanY = dragInfo.getObjectField("spanY")!!
                        val launcherCallBacks = param.args[0].callMethod("getLauncherCallbacks")
                        val dragController = param.args[0].callMethod("getDragController")!!
                        val isDraggingFromAssistant =
                            dragController.callMethodAs<Boolean>("isDraggingFromAssistant")
                        val isDraggingToAssistant =
                            dragController.callMethodAs<Boolean>("isDraggingToAssistant")
                        param.result =
                            launcherCallBacks != null && !isDraggingFromAssistant && !isDraggingToAssistant && spanX != 1
//                        launcherCallBacks != null && !isDraggingFromAssistant && !isDraggingToAssistant && spanX % 2 == 0 && (spanX != 2 || spanY == 2)
                    }
                }
            }
    }
}