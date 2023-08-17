package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.safeDexKitBridge
import java.lang.reflect.Method

object ScreenTime : HookRegister() {
    private lateinit var cls: Class<*>
    private lateinit var method1: Method
    private lateinit var method2: Method
    override fun init() = hasEnable("screen_time") {
        cls = safeDexKitBridge.batchFindClassesUsingStrings {
            addQuery("qwq1", setOf("not support screenPowerSplit", "PowerRankHelperHolder"))
            matchType = MatchType.FULL
        }.values
            .flatten()
            .map { it.getClassInstance(classLoader) }
            .firstOrNull()!!

        method1 = safeDexKitBridge.batchFindMethodsUsingStrings {
            addQuery("qwq2", setOf("ishtar", "nuwa", "fuxi"))
            matchType = MatchType.FULL
        }.values
            .flatten()
            .map { it.getMethodInstance(classLoader) }
            .firstOrNull()!!

        safeDexKitBridge.findMethod {
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