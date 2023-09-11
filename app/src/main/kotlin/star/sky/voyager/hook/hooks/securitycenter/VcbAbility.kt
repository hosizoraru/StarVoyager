package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object VcbAbility : HookRegister() {
    private val vcb by lazy {
//        dexKitBridge.findMethodUsingString {
//            usingString = "persist.vendor.vcb.ability"
//            matchType = MatchType.FULL
//        }.firstOrNull()?.getMethodInstance(classLoader)
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals(
                    "persist.vendor.vcb.ability"
                )
            }
        }.firstOrNull()?.getMethodInstance(classLoader)
    }

    override fun init() = hasEnable("vcb_ability") {
        vcb?.createHook {
            returnConstant(true)
        }
    }
}