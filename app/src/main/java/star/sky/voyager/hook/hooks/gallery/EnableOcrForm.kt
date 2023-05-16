package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object EnableOcrForm : HookRegister() {
    override fun init() = hasEnable("enable_ocr_form") {
        loadClass("com.miui.gallery.util.RecognizeFormUtil").methodFinder().first {
            name == "isAvailable"
        }.createHook {
            before {
                it.result = true
            }
        }
    }
}