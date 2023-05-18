package star.sky.voyager.hook.hooks.maxmipad

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object SwitchPadMode : HookRegister() {
    override fun init() = hasEnable("restore_esc") {
        loadClass("com.android.server.input.InputManagerServiceStubImpl").methodFinder().first {
            name == "switchPadMode"
        }.createHook {
            before { param ->
                param.args[0] = false
            }
        }
    }
}