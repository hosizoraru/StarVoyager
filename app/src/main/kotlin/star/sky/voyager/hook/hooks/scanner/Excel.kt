package star.sky.voyager.hook.hooks.scanner

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object Excel : HookRegister() {
    override fun init() = hasEnable("excel") {
        loadClass("com.xiaomi.scanner.util.SPUtils").methodFinder()
            .filterByName("getFormModule")
            .first().createHook {
                before {
                    it.result = true
                }
            }

        loadClass("com.xiaomi.scanner.settings.FeatureManager").methodFinder().filter {
            name in setOf("isSupportForm", "isAddFormRecognitionFunction")
        }.toList().createHooks {
            before {
                it.result = true
            }
        }
    }
}