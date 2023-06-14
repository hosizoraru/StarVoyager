package star.sky.voyager.hook.hooks.powerkeeper

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object DoNotClearAppPlusP : HookRegister() {
    override fun init() = hasEnable("do_not_clear_app_plus") {
        loadClassOrNull("android.os.spc.PressureStateSettings")?.let {
            setStaticObject(it, "PROCESS_CLEANER_ENABLED", false)
        }
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
        loadClass("com.android.server.am.PhantomProcessList").methodFinder().first {
            name == "trimPhantomProcessesIfNecessary"
        }.createHook {
            before {
                it.result = null
            }
        }
    }
}