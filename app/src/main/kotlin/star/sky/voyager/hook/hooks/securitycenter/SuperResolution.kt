package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object SuperResolution : HookRegister() {
    override fun init() = hasEnable("super_resolution") {
//        dexKitBridge.batchFindClassesUsingStrings {
//            addQuery("qwq", setOf("ro.vendor.media.video.frc.support"))
//        }.forEach { (_, classes) ->
//            classes
        dexKitBridge.findClass {
            matcher {
                usingStrings = listOf("ro.vendor.media.video.frc.support")
            }
        }.map {
            val qaq = it.getInstance(classLoader)
            var counter = 0
            dexKitBridge.findMethod {
//                    methodDeclareClass = qaq.name
//                    methodReturnType = "boolean"
//                    methodParamTypes = arrayOf("java.lang.String")
                matcher {
                    declaredClass = qaq.name
                    returnType = "boolean"
                    parameterTypes = listOf("java.lang.String")
                }
            }.forEach { methods ->
                counter++
                if (counter == 1) {
                    methods.getMethodInstance(classLoader).createHook {
                        returnConstant(true)
                    }
                }
            }
//                dexKitBridge.findMethodUsingString {
//                    methodDeclareClass = qaq.name
//                    usingString = "debug.config.media.video.ais.support"
//                }.single().getMethodInstance(classLoader).createHook {
//                    returnConstant(true)
//                }
            dexKitBridge.findMethod {
                matcher {
                    declaredClass = qaq.name
                    usingStrings = listOf("debug.config.media.video.ais.support")
                }
            }.first().getMethodInstance(classLoader).createHook {
                returnConstant(true)
            }
        }
//        }
    }
}