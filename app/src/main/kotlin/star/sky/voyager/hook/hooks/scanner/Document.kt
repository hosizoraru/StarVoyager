package star.sky.voyager.hook.hooks.scanner

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object Document : HookRegister() {
    override fun init() = hasEnable("document") {
        loadClass("com.xiaomi.scanner.settings.FeatureManager").methodFinder().first {
            name == "isAddDocumentModule"
        }.createHook {
            before {
                it.result = true
            }
        }
    }
}