package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object HDREnhance : HookRegister() {
    override fun init() = hasEnable("Unlock_HDR_Enhance") {
        loadClass("com.miui.gallery.domain.DeviceFeature").methodFinder().first {
            name == "isSupportHDREnhance"
        }.createHook {
            after {
                it.result = true
            }
        }
    }
}