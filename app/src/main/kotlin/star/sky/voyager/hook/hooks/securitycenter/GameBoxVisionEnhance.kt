package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object GameBoxVisionEnhance : HookRegister() {
    private val gameBoxVisionEnhanceUtilsCls by lazy {
        loadClass("com.miui.gamebooster.utils.GameBoxVisionEnhanceUtils")
    }

    private val l6g by lazy {
        dexKitBridge.findClass {
            matcher {
                addUsingStringsEquals("GBToolsFunctionManager")
            }
        }.first().getInstance(classLoader)
    }

    private val g by lazy {
        l6g.methodFinder()
            .filterByParamCount(3)
            .first()
    }

    private val invokeMethod by lazy {
        dexKitBridge.findMethod {
            matcher {
                declaredClass = gameBoxVisionEnhanceUtilsCls.name
                returnType = "boolean"
                paramTypes = emptyList()
                addCall {
                    declaredClass = l6g.name
                    returnType = g.returnType.name
                }
            }
        }.map { it.getMethodInstance(classLoader) }
    }

    override fun init() = hasEnable("game_box_vision_enhance") {
        invokeMethod.createHooks {
            returnConstant(true)
        }
    }
}