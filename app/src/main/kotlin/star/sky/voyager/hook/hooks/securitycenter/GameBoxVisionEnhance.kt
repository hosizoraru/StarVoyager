package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import java.lang.reflect.Method

object GameBoxVisionEnhance : HookRegister() {
    //    private lateinit var u: Method
    private lateinit var l6g: Class<*>
    private lateinit var g: Method
    override fun init() = hasEnable("game_box_vision_enhance") {
        val gameBoxVisionEnhanceUtilsCls =
            loadClass("com.miui.gamebooster.utils.GameBoxVisionEnhanceUtils")

//        gameBoxVisionEnhanceUtilsCls.methodFinder()
//            .filterByReturnType(Boolean::class.java)
//            .filterByParamCount(0)
//            .toList().forEach { method1 ->
//                if (method1.isStatic) {
//                    u = method1
//                }
//            }

        dexKitBridge.batchFindClassesUsingStrings {
            addQuery("qwq1", setOf("GBToolsFunctionManager"))
            matchType = MatchType.FULL
        }.forEach { (_, classes1) ->
            classes1.map {
                l6g = it.getClassInstance(classLoader)
            }
        }

        g = l6g.methodFinder()
            .filterByParamCount(3)
            .first()

        dexKitBridge.findMethodInvoking {
            methodDeclareClass = l6g.name
            methodName = g.name
            methodReturnType = g.returnType.name
            beInvokedMethodDeclareClass = gameBoxVisionEnhanceUtilsCls.name
            beInvokedMethodReturnType = "boolean"
        }.forEach { (_, method2) ->
            method2.map {
                it.getMethodInstance(classLoader).createHook {
                    returnConstant(true)
                }
            }
        }
    }
}