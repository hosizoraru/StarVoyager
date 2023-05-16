package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object MonoChromeIcon : HookRegister() {
    override fun init() = hasEnable("mono_chrome_icon") {
        loadClass("com.miui.home.launcher.graphics.MonochromeUtils").methodFinder().first {
            name == "isSupportMonochrome"
        }.createHook {
            after {
                it.result = true
            }
        }
    }
}