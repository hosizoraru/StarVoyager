package star.sky.voyager.hook.hooks.scanner

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object Translation : HookRegister() {
    override fun init() = hasEnable("translation") {
        loadClass("com.xiaomi.scanner.settings.FeatureManager").methodFinder().filter {
            name in setOf("isAddTranslation", "isTranslationModuleAvailable")
        }.toList().createHooks {
            before {
                it.result = true
            }
        }
    }
}