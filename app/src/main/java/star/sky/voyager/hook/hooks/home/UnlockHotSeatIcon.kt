package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.EzXHelper
import star.sky.voyager.utils.api.hookBeforeMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object UnlockHotSeatIcon : HookRegister() {
    override fun init() = hasEnable("unlock_hot_seat") {
        "com.miui.home.launcher.DeviceConfig".hookBeforeMethod(
            EzXHelper.classLoader,
            "getHotseatMaxCount"
        ) {
            it.result = 99
        }
    }
}