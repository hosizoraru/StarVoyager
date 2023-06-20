package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object VideoEditor : HookRegister() {
    override fun init() = hasEnable("video_editor") {
        loadClass("com.miui.mediaeditor.api.MediaEditorApiHelper").methodFinder().filter {
            name in setOf("isVideoEditorAvailable", "isVlogAvailable")
        }.toList().createHooks {
            before {
                it.result = true
            }
        }
    }
}