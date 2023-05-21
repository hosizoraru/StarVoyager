package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.ClassLoaderProvider.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import star.sky.voyager.utils.yife.DexKit.loadDexKit

object RemoveReport : HookRegister() {
    override fun init() = hasEnable("remove_report") {
        loadDexKit()
        dexKitBridge.batchFindMethodsUsingStrings {
            addQuery("qwq", listOf("android.intent.action.VIEW", "com.xiaomi.market"))
            matchType = MatchType.FULL
        }.forEach { (_, classes) ->
            classes.map {
                it.getMethodInstance(classLoader)
            }.createHooks {
                returnConstant(false)
            }
        }
    }
}