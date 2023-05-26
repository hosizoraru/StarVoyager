package star.sky.voyager.hook.hooks.scanner

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object Translation : HookRegister() {
    override fun init() = hasEnable("enable_translation") {
        val qwq = loadClass("com.xiaomi.scanner.settings.FeatureManager")
        qwq.methodFinder().first {
            name == "isAddTranslation"
        }.createHook {
            before {
                it.result = true
            }
        }
        qwq.methodFinder().first {
            name == "isTranslationModuleAvailable"
        }.createHook {
            before {
                it.result = true
            }
        }
    }
}