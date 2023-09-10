package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import org.luckypray.dexkit.query.enums.StringMatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object DynamicPerformance : HookRegister() {
    private val d by lazy {
//        dexKitBridge.findMethodUsingString {
//            usingString = "persist.sys.smartop.support_dynamic_performance"
//            matchType = MatchType.FULL
//        }.firstOrNull()?.getMethodInstance(classLoader)!!
        dexKitBridge.findMethod {
            matcher {
                usingStrings = listOf("persist.sys.smartop.support_dynamic_performance")
                StringMatchType.Equals
            }
        }.firstOrNull()?.getMethodInstance(classLoader)!!
    }

    private val c by lazy {
//        dexKitBridge.findMethodUsingString {
//            methodDeclareClass = d.declaringClass.name
//            usingString = "PREF_KEY_DYNAMIC_PERFORMANCE_SWITCH"
//            methodReturnType = d.returnType.name
//        }.firstOrNull()?.getMethodInstance(classLoader)
        dexKitBridge.findMethod {
            matcher {
                usingStrings = listOf("persist.sys.smartop.support_dynamic_performance")
                StringMatchType.Equals
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