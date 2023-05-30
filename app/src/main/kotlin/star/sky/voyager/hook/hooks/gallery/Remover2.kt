package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object Remover2 : HookRegister() {
    override fun init() = hasEnable("remover2") {
        loadClass("com.miui.gallery.editor.photo.app.remover2.sdk.Remover2CheckHelper").methodFinder()
            .first {
                name == "isRemover2Support"
            }.createHook {
                before {
                    it.result = true
                }
            }
    }
}