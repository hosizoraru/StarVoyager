package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object UnlockHotSeatIcon : HookRegister() {
    override fun init() = hasEnable("unlock_hot_seat") {
        loadClass("com.miui.home.launcher.DeviceConfig").methodFinder()
            .filterByName("getHotseatMaxCount")
            .first().createHook {
                before {
                    it.result = 99
                }
            }
    }
}