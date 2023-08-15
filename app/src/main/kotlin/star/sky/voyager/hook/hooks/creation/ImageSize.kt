package star.sky.voyager.hook.hooks.creation

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object ImageSize : HookRegister() {
    override fun init() = hasEnable("creation_image_size") {
        loadClass("com.miui.creation.editor.filemanager.strategy.SuniaImportStrategy").methodFinder()
            .filterByName("setCheckImageSize")
            .first().createHook {
                before {
                    it.args[0] = false
                }
            }
    }
}