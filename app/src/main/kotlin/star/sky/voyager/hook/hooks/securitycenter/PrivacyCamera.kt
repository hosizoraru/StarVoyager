package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.dexKitBridge


object PrivacyCamera : HookRegister() {
    private val privateCls by lazy {
        dexKitBridge.findClass {
            matcher {
                usingStrings = listOf("persist.sys.privacy_camera")
            }
        }.first().getInstance(classLoader)
    }

    private val R0 by lazy {
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals("persist.sys.privacy_camera")
            }
        }.first().getMethodInstance(classLoader)
    }

    private val invokeMethod by lazy {
        dexKitBridge.findMethod {
            matcher {
                declaredClass = privateCls.name
                paramTypes = emptyList()
                returnType = "boolean"
                addInvoke {
                    returnType = R0.returnType.name
                    paramTypes = listOf(R0.parameterTypes[0].name)
                    declaredClass = privateCls.name
                }
            }
        }.map { it.getMethodInstance(classLoader) }.toList()
    }

    override fun init() = hasEnable("privacy_camera") {
        R0.createHook {
            before {
                it.args[0] = true
            }
        }

        invokeMethod.createHooks {
            returnConstant(true)
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