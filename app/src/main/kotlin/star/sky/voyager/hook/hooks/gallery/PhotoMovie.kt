package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object PhotoMovie : HookRegister() {
    override fun init() = hasEnable("photo_movie") {
        loadClass("com.miui.mediaeditor.api.MediaEditorApiHelper").methodFinder().first {
            name == "isPhotoMovieAvailable"
        }.createHook {
            before {
                it.result = true
            }
        }

        loadClass("com.miui.gallery.domain.DeviceFeature").methodFinder().first {
            name == "isDeviceSupportPhotoMovie"
        }.createHook {
            before {
                it.result = true
            }
        }
    }
}