package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object VcbAbility : HookRegister() {
    override fun init() = hasEnable("vcb_ability") {
        dexKitBridge.findMethodUsingString {
            usingString = "persist.vendor.vcb.ability"
            matchType = MatchType.FULL
        }.single().getMethodInstance(classLoader).createHook {
            returnConstant(true)
        }
    }
}