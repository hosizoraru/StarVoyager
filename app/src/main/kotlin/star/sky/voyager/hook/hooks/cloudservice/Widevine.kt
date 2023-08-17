package star.sky.voyager.hook.hooks.cloudservice

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object Widevine : HookRegister() {
    override fun init() = hasEnable("unlock_widevine_l1") {
        dexKitBridge.findMethodUsingString {
            usingString = "persist.vendor.sys.pay.widevine"
            matchType = MatchType.FULL
        }.single().getMethodInstance(classLoader).createHook {
            returnConstant(true)
        }
    }
}