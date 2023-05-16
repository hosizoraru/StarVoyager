package star.sky.voyager.hook.hooks.scanner

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object EnablePpt : HookRegister() {
    override fun init() = hasEnable("enable_ocr2") {
        loadClass("com.xiaomi.scanner.settings.FeatureManager").methodFinder().first {
            name == "isAddTextExtractionFunction"
        }.createHook {
            before {
                it.result = true
            }
        }
    }
}