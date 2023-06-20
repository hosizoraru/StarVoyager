package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object Ocr : HookRegister() {
    override fun init() = hasEnable("ocr") {
        loadClass("com.miui.gallery.ui.photoPage.ocr.OCRHelper").methodFinder().filter {
            name in setOf("isSupportLocalOCR", "isSupportOCR")
        }.toList().createHooks {
            before {
                it.result = true
            }
        }
    }
}