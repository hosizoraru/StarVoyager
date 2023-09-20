package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.Log
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object ScreenTime : HookRegister() {
    private val cls by lazy {
        dexKitBridge.findClass {
            matcher {
                addUsingStringsEquals(
                    "not support screenPowerSplit", "PowerRankHelperHolder"
                )
            }
        }.first().getInstance(classLoader)
    }
    private val method1 by lazy {
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals(
                    "ishtar", "nuwa", "fuxi"
                )
            }
        }.first().getMethodInstance(classLoader)
    }
    private val method2 by lazy {
        dexKitBridge.findMethod {
            matcher {
                declaredClass = cls.name
                returnType = "boolean"
//                paramTypes = listOf() 2.0.0-rc3 已经修复此错误，可以使用
                paramCount = 0
            }
        }.map { it.getMethodInstance(classLoader) }.toList()
    }

    override fun init() = hasEnable("screen_time") {
        Log.i("methods2 :${method2}")
        method2.forEach {
            it.createHook {
                returnConstant(
                    when (it) {
                        method1 -> true
                        else -> false
                    }
                )
            }
        }
    }
}