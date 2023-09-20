package star.sky.voyager.hook.hooks.guardprovider

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.utils.api.replaceMethod
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.closeDexKit
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import java.lang.reflect.Method

class AntiDefraudAppManager : IXposedHookLoadPackage {

    @Throws(NoSuchMethodException::class)
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) =
        hasEnable("Anti_Defraud_App_Manager") {
            val map = mapOf(
                "AntiDefraudAppManager" to setOf(
                    "AntiDefraudAppManager",
                    "https://flash.sec.miui.com/detect/app"
                ),
            )
            val result = dexKitBridge.findMethod {
                matcher {
                    addUsingStringsEquals(
                        "AntiDefraudAppManager",
                        "https://flash.sec.miui.com/detect/app"
                    )
                }
            }
            val antiDefraudAppManagerDescriptor = result.first()
            val antiDefraudAppManagerMethod: Method =
                antiDefraudAppManagerDescriptor.getMethodInstance(lpparam.classLoader)
            antiDefraudAppManagerMethod.replaceMethod {
                return@replaceMethod null
            }
            closeDexKit()
        }
}