package star.sky.voyager.hook.hooks.cloudservice

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object Widevine : HookRegister() {
    private val WideVineL1 by lazy {
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals("persist.vendor.sys.pay.widevine")
            }
        }.firstOrNull()?.getMethodInstance(classLoader)
    }

    override fun init() = hasEnable("unlock_widevine_l1") {
        WideVineL1?.createHook {
            returnConstant(true)
        }
    }
}