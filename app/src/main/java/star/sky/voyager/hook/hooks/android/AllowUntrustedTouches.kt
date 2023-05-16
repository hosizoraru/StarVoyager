package star.sky.voyager.hook.hooks.android

import android.content.Context
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object AllowUntrustedTouches : HookRegister() {
    override fun init() = hasEnable("allow_untrusted_touches") {
        loadClass("android.hardware.input.InputManager").methodFinder().first {
            name == "getBlockUntrustedTouchesMode" && parameterTypes[0] == Context::class.java
        }.createHook {
            returnConstant(0)
        }
    }
}