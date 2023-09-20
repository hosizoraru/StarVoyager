package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object Macro : HookRegister() {
    override fun init() = hasEnable("macro_combo") {
        dexKitBridge.findMethod {
            matcher {
                usingStrings = listOf("pref_gb_unsupport_macro_apps")
                paramTypes = listOf("java.util.ArrayList")
                returnType = "void"
            }
        }.first().getMethodInstance(classLoader).createHook {
            before {
                it.result = ArrayList<String>()
            }
        }

        dexKitBridge.findClass {
            matcher {
                usingStrings = listOf("com.netease.sky.mi")
            }
        }.first().getInstance(classLoader).methodFinder()
            .filterByReturnType(Boolean::class.java)
            .filterByParamCount(1)
            .first().createHook {
                returnConstant(false)
            }

        dexKitBridge.findClass {
            matcher {
                usingStrings =
                    listOf("content://com.xiaomi.macro.MacroStatusProvider/game_macro_change")
            }
        }.first().getInstance(classLoader).methodFinder()
            .filterByReturnType(Boolean::class.java)
            .filterByParamCount(2)
            .first().createHook {
                returnConstant(true)
            }
    }
}