package star.sky.voyager.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object MaxFreeFormA : HookRegister() {
    override fun init() = hasEnable("max_free_form") {
        val MiuiFreeFormStackDisplayStrategyClass =
            loadClass("com.android.server.wm.MiuiFreeFormStackDisplayStrategy")

        MiuiFreeFormStackDisplayStrategyClass.methodFinder().first {
            name == "getMaxMiuiFreeFormStackCount"
        }.createHook {
            returnConstant(256)
        }

        MiuiFreeFormStackDisplayStrategyClass.methodFinder().first {
            name == "getMaxMiuiFreeFormStackCountForFlashBack"
        }.createHook {
            returnConstant(256)
        }

        loadClass("com.android.server.wm.MiuiFreeFormManagerService").methodFinder().first {
            name == "shouldStopStartFreeform"
        }.createHook {
            returnConstant(false)
        }
    }
}