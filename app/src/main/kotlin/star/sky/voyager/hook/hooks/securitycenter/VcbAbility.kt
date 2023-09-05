package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import org.luckypray.dexkit.query.enums.StringMatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object VcbAbility : HookRegister() {
    private val vcb by lazy {
//        dexKitBridge.findMethodUsingString {
//            usingString = "persist.vendor.vcb.ability"
//            matchType = MatchType.FULL
//        }.firstOrNull()?.getMethodInstance(classLoader)
        dexKitBridge.findMethod {
            matcher {
                usingStringsMatcher {
                    this.add {
                        this.value = "persist.vendor.vcb.ability"
                        StringMatchType.Equals
                    }
                }
            }
        }.firstOrNull()?.getMethodInstance(classLoader)
    }

    override fun init() = hasEnable("vcb_ability") {
        vcb?.createHook {
            returnConstant(true)
        }
    }
}