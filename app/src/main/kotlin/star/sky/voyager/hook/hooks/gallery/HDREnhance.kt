package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object HDREnhance : HookRegister() {
    override fun init() = hasEnable("hdr_enhance") {
        loadClass("com.miui.gallery.domain.DeviceFeature").methodFinder().filter {
            name in setOf(
                "isSupportHDREnhance",
                "isSupportDolbyVideo",
                "isHighEndDevice",
            )
        }.toList().createHooks {
            returnConstant(true)
        }
    }
}