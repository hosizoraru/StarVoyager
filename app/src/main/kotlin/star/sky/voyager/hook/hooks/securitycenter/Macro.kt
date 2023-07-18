package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.safeDexKitBridge

object Macro : HookRegister() {
    override fun init() = hasEnable("macro_combo") {
        safeDexKitBridge.findMethodUsingString {
            usingString = "pref_gb_unsupport_macro_apps"
            methodParamTypes = arrayOf("Ljava/util/ArrayList;")
            methodReturnType = "void"
        }.single().getMethodInstance(classLoader).createHook {
            before {
                it.result = ArrayList<String>()
            }
        }

        safeDexKitBridge.batchFindClassesUsingStrings {
            addQuery(
                "qwq1",
                setOf("com.netease.sky.mi")
            )
        }.forEach { (_, classes1) ->
            classes1.single().getClassInstance(classLoader).methodFinder()
                .filterByReturnType(Boolean::class.java)
                .filterByParamCount(1)
                .first().createHook {
                    returnConstant(false)
                }
        }

        safeDexKitBridge.batchFindClassesUsingStrings {
            addQuery(
                "qwq2",
                setOf(
                    "content://com.xiaomi.macro.MacroStatusProvider/game_macro_change"
                )
            )
        }.forEach { (_, classes2) ->
            classes2.single().getClassInstance(classLoader).methodFinder()
                .filterByReturnType(Boolean::class.java)
                .filterByParamCount(2)
                .first().createHook {
                    returnConstant(true)
                }
        }
    }
}