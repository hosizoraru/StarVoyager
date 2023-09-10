package star.sky.voyager.hook.hooks.settings

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import org.luckypray.dexkit.query.enums.StringMatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object HpLocation : HookRegister() {
    private val hp by lazy {
//        dexKitBridge.findMethodUsingString {
//            usingString = "persist.vendor.gnss.hpLocSetUI"
//            matchType = MatchType.FULL
//        }.map { it.getMethodInstance(classLoader) }.toList()
        dexKitBridge.findMethod {
            matcher {
                usingStrings = listOf("persist.vendor.gnss.hpLocSetUI")
                StringMatchType.Equals
            }
        }.map { it.getMethodInstance(classLoader) }.toList()
    }

    private val Zh by lazy {
//        dexKitBridge.findMethodUsingString {
//            usingString = "zh_CN"
//            matchType = MatchType.FULL
//        }.map { it.getMethodInstance(classLoader) }.toList()
        dexKitBridge.findMethod {
            matcher {
                usingStrings = listOf("zh_CN")
                StringMatchType.Equals
            }
        }.map { it.getMethodInstance(classLoader) }.toList()
    }

    override fun init() = hasEnable("hp_location") {
        setOf(hp, Zh).forEach {
            it.createHooks {
                returnConstant(true)
            }
        }
    }
}

//        loadClass("com.android.settings.location.XiaomiHpLocationController")
//            .methodFinder().filter {
//                name in setOf("hasXiaomiHpFeature", "isZh")
//            }.toList().createHooks {
//                returnConstant(true)
//            }