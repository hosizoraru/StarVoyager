package star.sky.voyager.hook.hooks.weather

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.safeDexKitBridge
import java.util.Locale

object MoreCard : HookRegister() {
    private lateinit var toolsCls: Class<*>
    override fun init() = hasEnable("more_card") {
        safeDexKitBridge.batchFindClassesUsingStrings {
            addQuery("qwq", setOf("ro.miui.ui.version.name", "Wth2:ToolUtils"))
            matchType = MatchType.FULL
        }.forEach { (_, classes) ->
            classes.map {
                toolsCls = it.getClassInstance(classLoader)
            }
        }

        safeDexKitBridge.findMethod {
            methodDeclareClass = toolsCls.name
            methodParamTypes = arrayOf("android.content.Context")
            methodReturnType = "java.util.Locale"
        }.single().getMethodInstance(classLoader).createHook {
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