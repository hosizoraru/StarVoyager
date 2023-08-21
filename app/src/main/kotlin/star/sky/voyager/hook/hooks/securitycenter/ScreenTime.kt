package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import java.lang.reflect.Method

object ScreenTime : HookRegister() {
    private val cls by lazy {
        dexKitBridge.batchFindClassesUsingStrings {
            addQuery("qwq1", setOf("not support screenPowerSplit", "PowerRankHelperHolder"))
            matchType = MatchType.FULL
        }.values
            .flatten()
            .map { it.getClassInstance(classLoader) }
            .firstOrNull()!!
    }
    private val method1 by lazy {
        dexKitBridge.batchFindMethodsUsingStrings {
            addQuery("qwq2", setOf("ishtar", "nuwa", "fuxi"))
            matchType = MatchType.FULL
        }.values
            .flatten()
            .map { it.getMethodInstance(classLoader) }
            .firstOrNull()!!
    }
    private lateinit var method2: Method
    override fun init() = hasEnable("screen_time") {
        dexKitBridge.findMethod {
            methodDeclareClass = cls.name
            methodReturnType = "boolean"
            methodParamTypes = arrayOf()
        }.forEach { methods ->
            method2 = methods.getMethodInstance(classLoader)
            method2.createHook {
                returnConstant(
                    when (method2) {
                        method1 -> true
                        else -> false
                    }
                )
            }
        }
    }
}