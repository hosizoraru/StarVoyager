package star.sky.voyager.hook.hooks.home

import android.graphics.RectF
import com.github.kyuubiran.ezxhelper.EzXHelper
import star.sky.voyager.utils.api.callMethod
import star.sky.voyager.utils.api.callStaticMethod
import star.sky.voyager.utils.api.findClass
import star.sky.voyager.utils.api.hookAfterMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable

object TaskViewCardSize : HookRegister() {
    override fun init() = hasEnable("home_anim_ratio_binding") {
        val value = getInt("home_task_view_card_size_vertical", 100).toFloat() / 100f
        val value1 = getInt("home_task_view_card_size_horizontal1", 100).toFloat() / 100f
        val value2 = getInt("home_task_view_card_size_horizontal2", 100).toFloat() / 100f

        "com.miui.home.recents.views.TaskStackViewsAlgorithmVertical".hookAfterMethod(
            EzXHelper.classLoader,
            "scaleTaskView", RectF::class.java
        ) {
            "com.miui.home.recents.util.Utilities".findClass(EzXHelper.classLoader)
                .callStaticMethod(
                    "scaleRectAboutCenter", it.args[0], value
                )
        }

        "com.miui.home.recents.views.TaskStackViewsAlgorithmHorizontal".hookAfterMethod(
            EzXHelper.classLoader,
            "scaleTaskView", RectF::class.java,
        ) {
            "com.miui.home.recents.util.Utilities".findClass(EzXHelper.classLoader)
                .callStaticMethod(
                    "scaleRectAboutCenter",
                    it.args[0],
                    if (it.thisObject.callMethod("isLandscapeVisually") as Boolean) value2 else value1
                )
        }
    }
}