package star.sky.voyager.hook.hooks.taplus

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object HorizontalContentExtension : HookRegister() {
    override fun init() = hasEnable("horizontal_content_extension") {
        loadClass("com.miui.contentextension.services.TextContentExtensionService").methodFinder()
            .filterByName("isScreenPortrait")
            .first().createHook {
                after {
                    it.result = true
                }
            }
    }
}