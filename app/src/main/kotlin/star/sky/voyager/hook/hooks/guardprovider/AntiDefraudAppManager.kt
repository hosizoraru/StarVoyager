package star.sky.voyager.hook.hooks.guardprovider

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.luckypray.dexkit.DexKitBridge
import star.sky.voyager.utils.api.replaceMethod
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.loadDexKit
import java.lang.reflect.Method

class AntiDefraudAppManager : IXposedHookLoadPackage {

    @Throws(NoSuchMethodException::class)
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) =
        hasEnable("Anti_Defraud_App_Manager") {
            loadDexKit()
            DexKitBridge.create(lpparam.appInfo.sourceDir)?.use { bridge ->
                val map = mapOf(
                    "AntiDefraudAppManager" to setOf(
                        "AntiDefraudAppManager",
                        "https://flash.sec.miui.com/detect/app"
                    ),
                )

                val resultMap = bridge.batchFindMethodsUsingStrings {
                    queryMap(map)
                }

                val antiDefraudAppManager = resultMap["AntiDefraudAppManager"]!!
                assert(antiDefraudAppManager.size == 1)
                val antiDefraudAppManagerDescriptor = antiDefraudAppManager.first()
                val antiDefraudAppManagerMethod: Method =
                    antiDefraudAppManagerDescriptor.getMethodInstance(lpparam.classLoader)
                antiDefraudAppManagerMethod.replaceMethod {
                    return@replaceMethod null
                }
            }
        }
}