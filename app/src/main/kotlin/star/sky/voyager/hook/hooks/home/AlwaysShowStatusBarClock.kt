package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object AlwaysShowStatusBarClock : HookRegister() {
    override fun init() = hasEnable("home_time") {
        val workspaceClass = loadClass("com.miui.home.launcher.Workspace")
        val methodNames =
            listOf("isScreenHasClockGadget", "isScreenHasClockWidget", "isClockWidget")

        methodNames.forEach { methodName ->
            val result = runCatching {
                workspaceClass.methodFinder()
                    .filterByName(methodName)
                    .first()
                    .createHook {
                        before {
                            it.result = false
                        }
                    }
            }
            if (result.isSuccess) return@forEach
        }
    }
}