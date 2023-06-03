package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object Ocr : HookRegister() {
    override fun init() = hasEnable("ocr") {
        val ocrHelperClass = loadClass("com.miui.gallery.ui.photoPage.ocr.OCRHelper")

        ocrHelperClass.methodFinder()
            .filterByName("isSupportLocalOCR")
            .first().createHook {
                before {
                    it.result = true
                }
            }
        ocrHelperClass.methodFinder()
            .filterByName("isSupportOCR")
            .first().createHook {
                before {
                    it.result = true
                }
            }
    }
}