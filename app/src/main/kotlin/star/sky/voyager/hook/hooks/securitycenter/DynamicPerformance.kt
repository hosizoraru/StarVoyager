package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object DynamicPerformance : HookRegister() {
    private val d by lazy {
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals(
                    "persist.sys.smartop.support_dynamic_performance"
                )
            }
        }.firstOrNull()?.getMethodInstance(classLoader)!!
    }

    private val c by lazy {
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals(
                    "persist.sys.smartop.support_dynamic_performance"
                )
                declaredClass = d.declaringClass.name
                returnType = d.returnType.name
            }
        }.firstOrNull()?.getMethodInstance(classLoader)
    }

    override fun init() = hasEnable("dynamic_performance") {
        setOf(c, d).forEach {
            it?.createHook {
                returnConstant(true)
            }
        }
    }
}