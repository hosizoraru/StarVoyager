package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object VideoEditor : HookRegister() {
    override fun init() = hasEnable("enable_video_editor") {
        val qwq = loadClass("com.miui.mediaeditor.api.MediaEditorApiHelper")
        qwq.methodFinder().first {
            name == "isVideoEditorAvailable"
        }.createHook {
            after {
                it.result = true
            }
        }
        qwq.methodFinder().first {
            name == "isVlogAvailable"
        }.createHook {
            after {
                it.result = true
            }
        }
    }
}