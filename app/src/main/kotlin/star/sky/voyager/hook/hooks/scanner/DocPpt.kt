package star.sky.voyager.hook.hooks.scanner

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object DocPpt : HookRegister() {
    override fun init() = hasEnable("doc_ppt") {
        loadClass("com.xiaomi.scanner.settings.FeatureManager").methodFinder()
            .filterByName("isPPTModuleAvailable")
            .first().createHook {
                returnConstant(true)
            }
    }
}