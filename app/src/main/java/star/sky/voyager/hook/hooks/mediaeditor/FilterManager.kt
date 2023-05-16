package star.sky.voyager.hook.hooks.mediaeditor

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.FieldFinder.`-Static`.fieldFinder
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object FilterManager : HookRegister() {
    override fun init() = hasEnable("filter_manager") {
        loadClass("com.miui.gallery.editor.photo.core.imports.filter.FilterManager").methodFinder().first {
            name == "g"
        }.createHook {
            before {
                loadClass("android.os.Build").fieldFinder().first {
                    type == String::class.java && name == "DEVICE"
                }.set(null,"wayne")
            }
        }
    }
}