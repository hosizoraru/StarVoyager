package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object FakeNonDefaultIcon : HookRegister() {
    override fun init() = hasEnable("fake_non_default_icon") {
        loadClass("com.miui.home.launcher.DeviceConfig").methodFinder()
            .filterByName("isDefaultIcon")
            .first().createHook {
                returnConstant(false)
            }
    }
}