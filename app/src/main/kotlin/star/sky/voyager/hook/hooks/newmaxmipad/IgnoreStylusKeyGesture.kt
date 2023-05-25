package star.sky.voyager.hook.hooks.newmaxmipad

import com.github.kyuubiran.ezxhelper.ClassUtils.loadFirstClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object IgnoreStylusKeyGesture : HookRegister() {
    override fun init() = hasEnable("ignore_stylus_key_gesture") {
        val clazzMiuiStylusPageKeyListener = loadFirstClass(
            "com.miui.server.input.stylus.MiuiStylusPageKeyListener",
            "com.miui.server.stylus.MiuiStylusPageKeyListener"
        )
        val methodNames =
            setOf("isPageKeyEnable", "needInterceptBeforeDispatching", "shouldInterceptKey")
        clazzMiuiStylusPageKeyListener.methodFinder().filter {
            name in methodNames
        }.toList().createHooks {
            returnConstant(false)
        }
    }
}