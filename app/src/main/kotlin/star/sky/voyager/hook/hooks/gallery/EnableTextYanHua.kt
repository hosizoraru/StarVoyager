package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object EnableTextYanHua : HookRegister() {
    override fun init() = hasEnable("enable_text_yan_hua") {
        loadClass("com.miui.gallery.domain.SkyCheckHelper").methodFinder().first {
            name == "isSupportTextYanhua"
        }.createHook {
            after {
                it.result = true
            }
        }
    }
}