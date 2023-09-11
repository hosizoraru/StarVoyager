package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object RiskPkg : HookRegister() {
    private val pkg by lazy {
//        dexKitBridge.batchFindMethodsUsingStrings {
//            addQuery("qwq", setOf("riskPkgList", "key_virus_pkg_list", "show_virus_notification"))
//            matchType = MatchType.FULL
//        }.map { (_, methods) -> methods }.flatten().map { it.getMethodInstance(classLoader) }
//            .toList()
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals(
                    "riskPkgList", "key_virus_pkg_list", "show_virus_notification"
                )
            }
        }.map { it.getMethodInstance(classLoader) }.toList()
    }

    override fun init() = hasEnable("remove_risk_pkg") {
        pkg.createHooks {
            before { param ->
                param.result = null
            }
        }
    }
}