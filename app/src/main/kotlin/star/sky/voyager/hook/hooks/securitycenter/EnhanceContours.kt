package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.safeDexKitBridge

object EnhanceContours : HookRegister() {
    override fun init() = hasEnable("enhance_contours") {
        safeDexKitBridge.batchFindClassesUsingStrings {
            addQuery("qwq", setOf("ro.vendor.media.video.frc.support"))
        }.forEach { (_, classes) ->
            classes.map {
                val qaq = it.getClassInstance(classLoader)
                var counter = 0
                safeDexKitBridge.findMethod {
                    methodDeclareClass = qaq.name
                    methodReturnType = "boolean"
                    methodParamTypes = arrayOf("java.lang.String")
                }.forEach { methods ->
                    counter++
                    if (counter == 3) {
                        methods.getMethodInstance(classLoader).createHook {
                            returnConstant(true)
                        }
                    }
                }
                val tat = safeDexKitBridge.findMethodUsingString {
                    methodDeclareClass = qaq.name
                    usingString = "debug.config.media.video.ais.support"
                }.single().getMethodInstance(classLoader)
                val newChar = tat.name.toCharArray()
                for (i in newChar.indices) {
                    newChar[i]++
                }
                val newName = String(newChar)
                tat.declaringClass.methodFinder()
                    .filterByName(newName)
                    .first().createHook {
                        returnConstant(true)
                    }
            }
        }
    }
}