package star.sky.voyager.hook.hooks.guardprovider

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object Anti2 : HookRegister() {
    override fun init() = hasEnable("Anti_Defraud_App_Manager") {
        dexKitBridge.findMethodUsingString {
            usingString = "ro.miui.customized.region"
            matchType = MatchType.FULL
        }.firstOrNull()?.getMethodInstance(classLoader)?.createHook {
            returnConstant(false)
        }

        dexKitBridge.findMethodUsingString {
            usingString = "https://flash.sec.miui.com/detect/app"
            matchType = MatchType.FULL
        }.firstOrNull()?.getMethodInstance(classLoader)?.createHook {
            returnConstant(null)
        }
    }
}