package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object ShowAllApp : HookRegister() {
//    private val HideAppValid by lazy {
//        dexKitBridge.findMethod {
//            methodName = "isHideAppValid"
//            methodReturnType = "boolean"
//        }.map { it.getMethodInstance(classLoader) }
//            .toList()
//    }
//
//    private val uninstall by lazy {
//        dexKitBridge.batchFindClassesUsingStrings {
//            addQuery("qwq1", setOf("Can not uninstallApp:"))
//            matchType = MatchType.FULL
//        }.firstNotNullOf { (_, classes1) -> classes1.firstOrNull() }
//    }

    private val hideApp by lazy {
//        dexKitBridge.batchFindClassesUsingStrings {
//            addQuery("qwq1", setOf("appInfo.packageName", "com.android.fileexplorer"))
//            matchType = MatchType.FULL
//        }.firstNotNullOf { (_, classes1) -> classes1.firstOrNull() }
        dexKitBridge.findClass {
            matcher {
                addUsingStringsEquals(
                    "appInfo.packageName",
                    "com.android.fileexplorer"
                )
            }
        }.first().getInstance(classLoader)
    }

    private val hide by lazy {
//        dexKitBridge.findMethod {
//            methodName = "isHideAppValid"
//            methodReturnType = "boolean"
//            methodDeclareClass = hideApp.name
//        }.map { it.getMethodInstance(classLoader) }.first()
        dexKitBridge.findMethod {
            matcher {
                name = "isHideAppValid"
                returnType = "boolean"
                declaredClass = hideApp.name
            }
        }.first().getMethodInstance(classLoader)
    }

    override fun init() = hasEnable("show_all_app_dsm") {
        hide.createHook {
            returnConstant(true)
        }
    }
}