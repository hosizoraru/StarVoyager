package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object MEMC : HookRegister() {
    override fun init() = hasEnable("MEMC") {
        dexKitBridge.findClass {
            matcher {
                usingStrings = listOf("ro.vendor.media.video.frc.support")
            }
        }.map {
            val qaq = it.getInstance(classLoader)
            var counter = 0
            dexKitBridge.findMethod {
                matcher {
                    declaredClass = qaq.name
                    returnType = "boolean"
                    paramTypes = listOf("java.lang.String")
                }
            }.forEach { methods ->
                counter++
                if (counter == 5) {
                    methods.getMethodInstance(classLoader).createHook {
                        returnConstant(true)
                    }
                }
            }
        }
    }
}