package star.sky.voyager.hook.hooks.packageinstaller

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object DisableCountCheck : HookRegister() {
    override fun init() = hasEnable("package_installer_remove_check") {
        loadClass("com.miui.packageInstaller.model.RiskControlRules").methodFinder().first {
            name == "getCurrentLevel"
        }.createHook {
            before { param ->
                Log.i("Hooked getCurrentLevel, param result = ${param.result}")
                param.result = 0
            }
        }
    }
}