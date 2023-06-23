package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import star.sky.voyager.utils.yife.DexKit.loadDexKit

object RiskPkg : HookRegister() {
    override fun init() = hasEnable("remove_risk_pkg") {
        loadDexKit()
        dexKitBridge.batchFindMethodsUsingStrings {
            addQuery("qwq", setOf("riskPkgList", "key_virus_pkg_list", "show_virus_notification"))
            matchType = MatchType.FULL
        }.forEach { (_, methods) ->
            methods.map {
                it.getMethodInstance(classLoader).createHook {
                    before { param ->
                        param.result = null
                    }
                }
            }
        }
    }
}