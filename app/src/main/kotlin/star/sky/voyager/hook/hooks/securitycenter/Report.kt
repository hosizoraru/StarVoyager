package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.ClassLoaderProvider.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import org.luckypray.dexkit.query.enums.StringMatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object Report : HookRegister() {
    private val report by lazy {
//        dexKitBridge.batchFindMethodsUsingStrings {
//            addQuery("qwq", setOf("android.intent.action.VIEW", "com.xiaomi.market"))
//            matchType = MatchType.FULL
//        }.map { (_, methods) -> methods }.flatten().map { it.getMethodInstance(classLoader) }
//            .toList()
        dexKitBridge.findMethod {
            matcher {
                usingStrings = listOf("android.intent.action.VIEW", "com.xiaomi.market")
                StringMatchType.Equals
            }
        }.map { it.getMethodInstance(classLoader) }.toList()
    }

    override fun init() = hasEnable("remove_report") {
        report.createHooks {
            returnConstant(false)
        }
    }
}