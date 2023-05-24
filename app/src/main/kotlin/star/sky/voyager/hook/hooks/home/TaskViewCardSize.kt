package star.sky.voyager.hook.hooks.home

import android.graphics.RectF
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.callMethod
import star.sky.voyager.utils.api.callStaticMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable

object TaskViewCardSize : HookRegister() {
    override fun init() = hasEnable("home_task_view_card_size_binding") {
        // TODO: 改写这里为什么会失败呢？
        val value = getInt("home_task_view_card_size_vertical", 100).toFloat() / 100f
        val value1 = getInt("home_task_view_card_size_horizontal1", 100).toFloat() / 100f
        val value2 = getInt("home_task_view_card_size_horizontal2", 100).toFloat() / 100f
        val TaskStackViewsAlgorithmHorizontalClass =
            loadClass("com.miui.home.recents.views.TaskStackViewsAlgorithmHorizontal")
        val UtilitiesClass = loadClass("com.miui.home.recents.util.Utilities")
        TaskStackViewsAlgorithmHorizontalClass.methodFinder().first {
            name == "scaleTaskView" && parameterTypes[0] == RectF::class.java
        }.createHook {
            after {
                UtilitiesClass.callStaticMethod(
                    "scaleRectAboutCenter", it.args[0], value
                )
            }
        }
        TaskStackViewsAlgorithmHorizontalClass.methodFinder().first {
            name == "scaleTaskView" && parameterTypes[0] == RectF::class.java
        }.createHook {
            after {
                UtilitiesClass.callStaticMethod(
                    "scaleRectAboutCenter",
                    it.args[0],
                    if (it.thisObject.callMethod("isLandscapeVisually") as Boolean) value2 else value1
                )
            }
        }

    }
}