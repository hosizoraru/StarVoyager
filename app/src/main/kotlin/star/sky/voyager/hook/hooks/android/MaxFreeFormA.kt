package star.sky.voyager.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object MaxFreeFormA : HookRegister() {
    override fun init() = hasEnable("max_free_form") {
        val miuiFreeFormStackDisplayStrategyClass =
            loadClass("com.android.server.wm.MiuiFreeFormStackDisplayStrategy")

        miuiFreeFormStackDisplayStrategyClass.methodFinder()
            .filterByName("getMaxMiuiFreeFormStackCount")
            .first().createHook {
                returnConstant(256)
            }

        miuiFreeFormStackDisplayStrategyClass.methodFinder()
            .filterByName("getMaxMiuiFreeFormStackCountForFlashBack")
            .first().createHook {
                returnConstant(256)
            }

        loadClass("com.android.server.wm.MiuiFreeFormManagerService").methodFinder()
            .filterByName("shouldStopStartFreeform")
            .first().createHook {
                returnConstant(false)
            }
    }
}