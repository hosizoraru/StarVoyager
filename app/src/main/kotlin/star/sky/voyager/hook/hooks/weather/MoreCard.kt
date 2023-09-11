package star.sky.voyager.hook.hooks.weather

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import java.util.Locale

object MoreCard : HookRegister() {
    private val toolsCls by lazy {
//        dexKitBridge.batchFindClassesUsingStrings {
//            addQuery("qwq", setOf("ro.miui.ui.version.name", "Wth2:ToolUtils"))
//            matchType = MatchType.FULL
//        }.firstNotNullOf { (_, classes1) -> classes1.firstOrNull() }
        dexKitBridge.findClass {
            matcher {
                addUsingStringsEquals(
                    "ro.miui.ui.version.name", "Wth2:ToolUtils"
                )
            }
        }.first()
    }

    private val locale by lazy {
        dexKitBridge.findMethod {
//            methodDeclareClass = toolsCls.name
//            methodParamTypes = arrayOf("android.content.Context")
//            methodReturnType = "java.util.Locale"
            matcher {
                declaredClass = toolsCls.name
                paramTypes = listOf("android.content.Context")
                returnType = "java.util.Locale"
            }
        }.firstOrNull()?.getMethodInstance(classLoader)
    }

    override fun init() = hasEnable("more_card") {
        locale?.createHook {
            before {
                returnConstant(Locale("zh", "CN"))
            }
        }
    }
}

//        safeDexKitBridge.batchFindMethodsUsingStrings {
//            addQuery("qwq", setOf("ro.miui.ui.version.name", "Wth2:ToolUtils"))
//            matchType = MatchType.FULL
//        }.forEach { (_, methods) ->
//            methods.map {
//                it.getMethodInstance(classLoader).createHook {
//                    returnConstant("v140")
//                }
//            }
//        }