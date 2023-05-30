package star.sky.voyager.hook.hooks.scanner

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object Excel : HookRegister() {
    override fun init() = hasEnable("excel") {
        val qwq = loadClass("com.xiaomi.scanner.settings.FeatureManager")
        loadClass("com.xiaomi.scanner.util.SPUtils").methodFinder().first {
            name == "getFormModule"
        }.createHook {
            before {
                it.result = true
            }
        }
        qwq.methodFinder().first {
            name == "isSupportForm"
        }.createHook {
            before {
                it.result = true
            }
        }
        qwq.methodFinder().first {
            name == "isAddFormRecognitionFunction"
        }.createHook {
            before {
                it.result = true
            }
        }
    }
}