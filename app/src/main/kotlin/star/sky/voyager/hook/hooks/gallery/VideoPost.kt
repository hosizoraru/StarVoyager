package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object VideoPost : HookRegister() {
    override fun init() = hasEnable("video_post") {
        loadClass("com.miui.mediaeditor.api.MediaEditorApiHelper").methodFinder()
            .filterByName("isVideoPostAvailable")
            .first().createHook {
                before {
                    it.result = true
                }
            }
    }
}