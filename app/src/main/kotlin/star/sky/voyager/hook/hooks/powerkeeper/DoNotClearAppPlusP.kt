package star.sky.voyager.hook.hooks.powerkeeper

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object DoNotClearAppPlusP : HookRegister() {
    override fun init() = hasEnable("do_not_clear_app_plus") {
        ClassUtils.loadClassOrNull("android.os.spc.PressureStateSettings")?.let {
            ClassUtils.setStaticObject(it, "PROCESS_CLEANER_ENABLED", false)
        }
        ClassUtils.loadClass("com.android.server.am.ActivityManagerService").methodFinder().first {
            name == "checkExcessivePowerUsage"
        }.createHook {
            before {
                it.result = null
            }
        }
        ClassUtils.loadClass("com.android.server.am.OomAdjuster").methodFinder().first {
            name == "shouldKillExcessiveProcesses"
        }.createHook {
            before {
                it.result = false
            }
        }
        ClassUtils.loadClass("com.android.server.am.PhantomProcessList").methodFinder().first {
            name == "trimPhantomProcessesIfNecessary"
        }.createHook {
            before {
                it.result = null
            }
        }
    }
}