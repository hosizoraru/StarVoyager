package star.sky.voyager.hook.hooks.weather

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object HighAnimation : HookRegister() {
    override fun init() = hasEnable("high_animation") {
        loadClass("miuix.animation.utils.DeviceUtils").methodFinder()
            .filterByName("transDeviceLevel")
            .first().createHook {
                returnConstant(2)
            }
    }
}