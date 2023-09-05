package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import org.luckypray.dexkit.query.enums.StringMatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object GameBoxVisionEnhance : HookRegister() {
    private val gameBoxVisionEnhanceUtilsCls by lazy {
        loadClass("com.miui.gamebooster.utils.GameBoxVisionEnhanceUtils")
    }
    //    private lateinit var u: Method
//    private lateinit var l6g: Class<*>
    private val l6g by lazy {
        dexKitBridge.findClass {
            matcher {
                usingStrings = listOf("GBToolsFunctionManager")
                StringMatchType.Equals
            }
        }.first().getInstance(classLoader)
    }

    //    private lateinit var g: Method
    private val g by lazy {
        l6g.methodFinder()
            .filterByParamCount(3)
            .first()
    }

    private val invokeMethod by lazy {
        dexKitBridge.findMethod {
            matcher {
//                declaredClass = l6g.name
//                name = g.name
//                returnType = g.returnType.name
                declaredClass = gameBoxVisionEnhanceUtilsCls.name
                returnType = "boolean"
                parameterTypes = emptyList()
//                addInvoke {
////                    declaredClass = gameBoxVisionEnhanceUtilsCls.name
////                    returnType = "boolean"
//                    declaredClass = l6g.name
//                    name = g.name
////                    returnType = g.returnType.name
//                }
                addCall {
                    declaredClass = l6g.name
                    returnType = g.returnType.name
                }
            }
        }.map { it.getMethodInstance(classLoader) }
    }
    override fun init() = hasEnable("game_box_vision_enhance") {
//        gameBoxVisionEnhanceUtilsCls.methodFinder()
//            .filterByReturnType(Boolean::class.java)
//            .filterByParamCount(0)
//            .toList().forEach { method1 ->
//                if (method1.isStatic) {
//                    u = method1
//                }
//            }

//        dexKitBridge.batchFindClassesUsingStrings {
//            addQuery("qwq1", setOf("GBToolsFunctionManager"))
//            matchType = MatchType.FULL
//        }.forEach { (_, classes1) ->
//            classes1.map {
//                l6g = it.getClassInstance(classLoader)
//            }
//        }

//        g = l6g.methodFinder()
//            .filterByParamCount(3)
//            .first()
//        Log.i("qwq!!!: $invokeMethod")

        invokeMethod.createHooks {
            returnConstant(true)
        }

//        dexKitBridge.findMethodInvoking {
//            methodDeclareClass = l6g.name
//            methodName = g.name
//            methodReturnType = g.returnType.name
//            beInvokedMethodDeclareClass = gameBoxVisionEnhanceUtilsCls.name
//            beInvokedMethodReturnType = "boolean"
//        }.forEach { (_, method2) ->
//            method2.map {
//                it.getMethodInstance(classLoader).createHook {
//                    returnConstant(true)
//                }
//            }
//        }
    }
}