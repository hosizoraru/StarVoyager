package star.sky.voyager.hook.hooks.scanner

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object Translation : HookRegister() {
    override fun init() = hasEnable("translation") {
        val qwq = loadClass("com.xiaomi.scanner.settings.FeatureManager")
        qwq.methodFinder()
            .filterByName("isAddTranslation")
            .first().createHook {
                before {
                    it.result = true
                }
            }
        qwq.methodFinder()
            .filterByName("isTranslationModuleAvailable")
            .first().createHook {
                before {
                    it.result = true
                }
            }
    }
}