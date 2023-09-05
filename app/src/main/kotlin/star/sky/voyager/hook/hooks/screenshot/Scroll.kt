package star.sky.voyager.hook.hooks.screenshot

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister

object Scroll : HookRegister() {
    override fun init() {
        val scrollAnnotationDisplayCls =
            loadClass("com.miui.annotation.app.ScrollAnnotationDisplay")
        val scrollAnnotationViewCls =
            loadClass("com.miui.annotation.app.ScrollAnnotationView")

        scrollAnnotationDisplayCls.methodFinder()
            .filterByReturnType(Boolean::class.java)
            .toList().createHooks {
                returnConstant(true)
            }

        scrollAnnotationViewCls.methodFinder()
            .filterByReturnType(Boolean::class.java)
            .toList().createHooks {
                returnConstant(true)
            }
    }
}