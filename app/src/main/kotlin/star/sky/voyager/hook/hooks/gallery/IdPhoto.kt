package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object IdPhoto : HookRegister() {
    override fun init() = hasEnable("enable_id_photo") {
        loadClass("com.miui.mediaeditor.api.MediaEditorApiHelper").methodFinder().first {
            name == "isIDPhotoAvailable"
        }.createHook {
            after {
                it.result = true
            }
        }
    }
}