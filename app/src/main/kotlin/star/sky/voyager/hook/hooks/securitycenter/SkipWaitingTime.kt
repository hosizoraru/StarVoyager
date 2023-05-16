package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object SkipWaitingTime : HookRegister() {
    override fun init() = hasEnable("skip_waiting_time") {
        loadClass("android.widget.TextView").methodFinder().first {
            name == "setText" && parameterCount == 4
        }.createHook {
            before {
                if (it.args.isNotEmpty() && it.args[0]?.toString()?.startsWith("确定(") == true
                ) {
                    it.args[0] = "确定"
                }
            }
        }

        loadClass("android.widget.TextView").methodFinder().first {
            name == "setEnabled" && parameterTypes[0] == Boolean::class.java
        }.createHook {
            before {
                it.args[0] = true
            }
        }
    }
}