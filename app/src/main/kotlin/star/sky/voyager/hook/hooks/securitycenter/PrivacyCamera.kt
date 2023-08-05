package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.safeDexKitBridge
import java.lang.reflect.Method


object PrivacyCamera : HookRegister() {
    private lateinit var privateCls: Class<*>
    private lateinit var R0: Method

    override fun init() = hasEnable("privacy_camera") {
        safeDexKitBridge.batchFindClassesUsingStrings {
            addQuery("qwq", setOf("persist.sys.privacy_camera"))
        }.forEach { (_, classes) ->
            classes.map {
                privateCls = it.getClassInstance(classLoader)
            }
        }

        R0 = safeDexKitBridge.findMethodUsingString {
            usingString = "persist.sys.privacy_camera"
            matchType = MatchType.FULL
        }.single().getMethodInstance(classLoader)

        R0.createHook {
            before {
                it.args[0] = true
            }
        }

//        Log.i("cls name: ${privateCls.name}")
//        Log.i("R0 name: ${R0.name}")
//        Log.i("R0 return type: ${R0.returnType.name}")
//        Log.i("R0 parameter type: ${R0.parameterTypes[0].name}")

        safeDexKitBridge.findMethodInvoking {
            methodDeclareClass = privateCls.name
            methodReturnType = R0.returnType.name
            methodParameterTypes = arrayOf(R0.parameterTypes[0].name)
            beInvokedMethodParameterTypes = null
            beInvokedMethodReturnType = "boolean"
            beInvokedMethodDeclareClass = privateCls.name
        }.forEach { (_, method1) ->
            method1.map { it1 ->
                it1.getMethodInstance(classLoader).createHook {
                    returnConstant(true)
                }
            }
        }
    }
}

//    @Volatile
//    private val methodNamesToHook: MutableSet<String> by lazy {
//        mutableSetOf()
//    }

//        privateCls.methodFinder().filterByName(R0.name).first().createHook {
//            before {
//                // 获取当前线程的调用栈
//                val stackTraceElements = Thread.currentThread().stackTrace
//
//                // 遍历调用栈，查找调用了 R0 方法的方法，并筛选出调用了 privateCls 中的方法
//                val filteredStackTrace = stackTraceElements.filter { stackTraceElement ->
//                    // 判断调用栈中的类名是否是 privateCls 或 privateCls 的内部方法
//                    stackTraceElement.className.startsWith(privateCls.name)
//                }
//
//                // 在这里可以对 filteredStackTrace 进行进一步处理，如使用 Log 输出或者保存到文件中，用于分析查看调用关系
//                for (stackTraceElement in filteredStackTrace) {
//                    val methodName = stackTraceElement.methodName
//                    // 将调用了 R0 方法的方法名添加到方法名列表
//                    methodNamesToHook.add(methodName)
////                    Log.i("Method $methodName in class ${stackTraceElement.className} called R0 method from privateCls")
//                }
//            }
//        }
//
//        privateCls.methodFinder()
//            .filter { name in methodNamesToHook }
//            .toList()
//            .createHooks {
//                returnConstant(true)
//            }