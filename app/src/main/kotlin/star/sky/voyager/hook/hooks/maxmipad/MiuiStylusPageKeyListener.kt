package star.sky.voyager.hook.hooks.maxmipad

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import java.lang.reflect.Method

object MiuiStylusPageKeyListener : HookRegister() {
    override fun init() = hasEnable("ignore_stylus_key_gesture") {
        val methods = mutableListOf<Method>()
        val className =
            "com.miui.server.input.stylus.MiuiStylusPageKeyListener"
        val methodNames = setOf(
            "isPageKeyEnable", "needInterceptBeforeDispatching", "shouldInterceptKey"
        )
        methodNames.forEach { methodName ->
            kotlin.runCatching {
                loadClass(className).methodFinder().first {
                    name == methodName
                }
            }.getOrNull()?.let { method ->
                methods.add(method)
            }
        }
        methods.createHooks {
            returnConstant(false)
        }
    }
}