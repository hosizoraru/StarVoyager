package star.sky.voyager.hook.hooks.packageinstaller

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object DisableCountCheck : HookRegister() {
    override fun init() = hasEnable("package_installer_remove_check") {
        val riskControlRules =
            loadClass("com.miui.packageInstaller.model.RiskControlRules")
        try {
            riskControlRules.methodFinder()
                .filterByName("getCurrentLevel")
                .first().createHook {
                    returnConstant(0)
                }
        } catch (_: Throwable) {

        }
    }
}