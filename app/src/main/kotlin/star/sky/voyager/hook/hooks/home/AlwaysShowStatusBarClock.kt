package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object AlwaysShowStatusBarClock : HookRegister() {
    override fun init() = hasEnable("home_time") {
        try {
            loadClass("com.miui.home.launcher.Workspace").methodFinder().first {
                name == "isScreenHasClockGadget"
            }
        } catch (e: Exception) {
            loadClass("com.miui.home.launcher.Workspace").methodFinder().first {
                name == "isScreenHasClockWidget"
            }
        } catch (e: Exception) {
            loadClass("com.miui.home.launcher.Workspace").methodFinder().first {
                name == "isClockWidget"
            }
        }.createHook {
            before {
                it.result = false
            }
        }
    }
}