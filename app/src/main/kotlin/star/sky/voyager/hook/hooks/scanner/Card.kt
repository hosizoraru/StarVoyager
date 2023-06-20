package star.sky.voyager.hook.hooks.scanner

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object Card : HookRegister() {
    override fun init() = hasEnable("card") {
        loadClass("com.xiaomi.scanner.settings.FeatureManager").methodFinder().filter {
            name in setOf("isAddBusinessCard", "isBusinessCardModuleAvailable")
        }.toList().createHooks {
            before {
                it.result = true
            }
        }
    }
}