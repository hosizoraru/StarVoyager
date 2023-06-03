package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object SkipWaitingTime : HookRegister() {
    override fun init() = hasEnable("skip_waiting_time") {
        loadClass("android.widget.TextView").methodFinder()
            .filterByName("setText")
            .filterByParamCount(4)
            .first().createHook {
                before {
                    if (it.args.isNotEmpty() && it.args[0]?.toString()?.startsWith("确定(") == true
                    ) {
                        it.args[0] = "确定"
                    }
                }
            }

        loadClass("android.widget.TextView").methodFinder()
            .filterByName("setEnabled")
            .filterByParamTypes(Boolean::class.java)
            .first().createHook {
                before {
                    it.args[0] = true
                }
            }
    }
}