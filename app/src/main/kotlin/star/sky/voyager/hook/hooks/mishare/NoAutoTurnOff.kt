package star.sky.voyager.hook.hooks.mishare

import android.content.Context
import com.github.kyuubiran.ezxhelper.ClassLoaderProvider.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object NoAutoTurnOff : HookRegister() {
    override fun init() = hasEnable("No_Auto_Turn_Off") {
        dexKitBridge.batchFindMethodsUsingStrings {
            addQuery("qwq", setOf("EnabledState", "mishare_enabled"))
            matchType = MatchType.FULL
        }.forEach { (_, classes) ->
            classes.map {
                it.getMethodInstance(classLoader)
            }.createHooks {
                before {
                    it.result = null
                }
            }
        }

        dexKitBridge.batchFindClassesUsingStrings {
            addQuery("qwq", setOf("null context", "cta_agree"))
            matchType = MatchType.FULL
        }.forEach { (_, classes) ->
            classes.map {
                it.getClassInstance(classLoader).methodFinder()
                    .filterByReturnType(Boolean::class.java)
                    .filterByParamCount(2)
                    .filterByParamTypes(Context::class.java, String::class.java)
                    .toList().createHooks {
                        before { param ->
                            if (param.args[1].equals("security_agree")) {
                                param.result = false
                            }
                        }
                    }
            }
        }
    }
}