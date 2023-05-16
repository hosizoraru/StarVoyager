package star.sky.voyager.hook.hooks.mishare

import com.github.kyuubiran.ezxhelper.ClassLoaderProvider.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import star.sky.voyager.utils.yife.DexKit.loadDexKit

object NoAutoTurnOff : HookRegister() {
    override fun init() = hasEnable("No_Auto_Turn_Off") {
        loadDexKit()
        dexKitBridge.batchFindMethodsUsingStrings {
            addQuery("qwq", listOf("EnabledState","mishare_enabled"))
            matchType = MatchType.FULL
        }.forEach { ( _, classes) ->
            classes.map {
                it.getMethodInstance(classLoader)
            }.createHooks {
                before {
                    it.result = null
                }
            }
        }
    }
}