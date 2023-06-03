package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object IdPhoto : HookRegister() {
    override fun init() = hasEnable("id_photo") {
        val idPhotoEntranceUtilsClass = loadClass("com.miui.gallery.domain.IDPhotoEntranceUtils")
        loadClass("com.miui.mediaeditor.api.MediaEditorApiHelper").methodFinder()
            .filterByName("isIDPhotoAvailable")
            .first().createHook {
                after {
                    it.result = true
                }
            }
        idPhotoEntranceUtilsClass.methodFinder()
            .filterByName("isDeviceSupportIDPhoto")
            .first().createHook {
                before { param ->
                    param.result = true
                }
            }
        idPhotoEntranceUtilsClass.methodFinder()
            .filterByName("getIdType")
            .first().createHook {
                before { param ->
                    param.result = 2
                }
            }
    }
}