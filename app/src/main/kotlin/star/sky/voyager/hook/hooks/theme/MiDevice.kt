package star.sky.voyager.hook.hooks.theme

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.MemberExtensions.isFinal
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object MiDevice : HookRegister() {
    private val g by lazy {
        dexKitBridge.batchFindClassesUsingStrings {
            addQuery("qwq1", setOf("isOnPcMode"))
        }.firstNotNullOf { (_, classes1) -> classes1.firstOrNull() }
            .getClassInstance(classLoader)
    }
    private val miString by lazy {
        g.declaredFields.filter { field1 ->
            field1.isFinal && field1.type == String::class.java
        }
    }
    private val ki by lazy {
        dexKitBridge.findMethodUsingString {
            usingString = "persist.sys.muiltdisplay_type"
            matchType = MatchType.FULL
            methodDeclareClass = g.name
        }.first().getMethodInstance(classLoader)
    }
    private val x2 by lazy {
        dexKitBridge.findMethodCaller {
            methodDeclareClass = g.name
            methodReturnType = "java.lang.String"
            callerMethodName = ki.name
        }.firstNotNullOf { (_, method) -> method.first().getMethodInstance(classLoader) }
    }
    private val pad by lazy {
        miString.forEach {
            if (it.get(null) == "MIPAD") {
                return@lazy it
            }
        }
    }
    private val fold by lazy {
        miString.forEach {
            if (it.get(null) == "MIFOLD") {
                return@lazy it
            }
        }
    }
    private val phone by lazy {
        miString.forEach {
            if (it.get(null) == "MIPHONE") {
                return@lazy it
            }
        }
    }

    override fun init() {
    }
}