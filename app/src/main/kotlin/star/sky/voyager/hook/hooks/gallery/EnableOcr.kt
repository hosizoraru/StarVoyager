package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object EnableOcr : HookRegister() {
    override fun init() = hasEnable("enable_ocr") {
        loadClass("com.miui.gallery.ui.photoPage.ocr.OCRHelper").methodFinder().first {
            name == "isSupportLocalOCR"
        }.createHook {
            after {
                it.result = true
            }
        }
        loadClass("com.miui.gallery.ui.photoPage.ocr.OCRHelper").methodFinder().first {
            name == "isSupportOCR"
        }.createHook {
            after {
                it.result = true
            }
        }
    }
}