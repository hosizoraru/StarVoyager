package star.sky.voyager.hook.hooks.mishare

import com.github.kyuubiran.ezxhelper.ClassLoaderProvider.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
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
    private val toastMethods by lazy {
        dexKitBridge.findMethod {
            matcher {
                declaredClass = toastClass.name
                returnType = "boolean"
                paramCount = 2
                paramTypes = listOf("android.content.Context", "java.lang.String")
            }
        }.map { it.getMethodInstance(classLoader) }.toList()
    }

    override fun init() = hasEnable("No_Auto_Turn_Off") {
        nullMethod.createHook {
            returnConstant(null)
        }

        toastMethods.createHooks {
            before { param ->
                if (param.args[1].equals("security_agree")) {
                    param.result = false
                }
            }
        }
    }
}