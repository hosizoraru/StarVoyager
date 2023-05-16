package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object EnablePdf : HookRegister() {
    override fun init() = hasEnable("enable_pdf") {
        loadClass("com.miui.gallery.request.PicToPdfHelper").methodFinder().first {
            name == "isPicToPdfSupport"
        }.createHook {
            after {
                it.result = true
            }
        }
    }
}