package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.safeDexKitBridge

object GunService : HookRegister() {
    override fun init() = hasEnable("gun_service") {
        safeDexKitBridge.batchFindClassesUsingStrings {
            addQuery("qwq", setOf("gb_game_collimator_status"))
        }.forEach { (_, classes) ->
            classes.map {
                val qaq = it.getClassInstance(classLoader)
                safeDexKitBridge.findMethod {
                    methodDeclareClass = qaq.name
                    methodReturnType = "boolean"
                    methodParamTypes = arrayOf("java.lang.String")
                }.single().getMethodInstance(classLoader).createHook {
                    returnConstant(true)
                }
            }
        }
    }
}