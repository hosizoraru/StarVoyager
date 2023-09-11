package star.sky.voyager.hook.hooks.mishare

import android.content.Context
import com.github.kyuubiran.ezxhelper.ClassLoaderProvider.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object NoAutoTurnOff : HookRegister() {
    private val nullMethod by lazy {
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals(
                    "EnabledState",
                    "mishare_enabled"
                )
            }
        }.map { it.getMethodInstance(classLoader) }.first()
    }

    private val toastClass by lazy {
        dexKitBridge.findClass {
            matcher {
                addUsingStringsEquals(
                    "null context",
                    "cta_agree"
                )
            }
        }.map { it.getInstance(classLoader) }.first()
    }
    override fun init() = hasEnable("No_Auto_Turn_Off") {
        nullMethod.createHook {
            before {
                it.result = null
            }
        }

        toastClass.methodFinder()
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