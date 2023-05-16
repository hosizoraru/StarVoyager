package star.sky.voyager.hook.hooks.powerkeeper

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object DoNotClearApp : HookRegister() {
    override fun init() = hasEnable("do_not_clear_app") {
        loadClass("miui.process.ProcessManager").methodFinder().first {
            name == "kill"
        }.createHook {
            before {
                it.result = false
            }
        }
    }
}