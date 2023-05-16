package star.sky.voyager.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object DoNotClearAppPlusA : HookRegister() {
    override fun init() = hasEnable("do_not_clear_app_plus") {
        loadClass("com.android.server.am.ActivityManagerService").methodFinder().first {
            name == "checkExcessivePowerUsage"
        }.createHook {
            before {
                it.result = null
            }
        }
        loadClass("com.android.server.am.OomAdjuster").methodFinder().first {
            name == "shouldKillExcessiveProcesses"
        }.createHook {
            before {
                it.result = false
            }
        }
        loadClass("com.android.server.am.OomAdjuster").methodFinder().first {
            name == "updateAndTrimProcessLSP"
        }.createHook {
            before {
                it.args[2] = 0
            }
        }
        loadClass("com.android.server.am.PhantomProcessList").methodFinder().first {
            name == "trimPhantomProcessesIfNecessary"
        }.createHook {
            before {
                it.result = null
            }
        }
        loadClass("com.android.server.am.SystemPressureController").methodFinder().first {
            name == "nStartPressureMonitor"
        }.createHook {
            before {
                it.result = null
            }
        }
        loadClass("com.android.server.wm.RecentTasks").methodFinder().first {
            name == "trimInactiveRecentTasks"
        }.createHook {
            before {
                it.result = null
            }
        }
    }
}