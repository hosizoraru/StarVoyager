package star.sky.voyager.hook.hooks.powerkeeper

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object PreventRecoveryOfBatteryOptimizationWhitelist : HookRegister() {
    override fun init() = hasEnable("prevent_recovery_of_battery_optimization_white_list") {
        loadClass("com.miui.powerkeeper.statemachine.ForceDozeController").methodFinder()
            .filterByName("restoreWhiteListAppsIfQuitForceIdle")
            .first().createHook {
                returnConstant(null)
            }
    }
}