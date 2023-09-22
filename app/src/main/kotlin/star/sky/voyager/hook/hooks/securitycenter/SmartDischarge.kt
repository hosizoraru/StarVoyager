package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object SmartDischarge : HookRegister() {
    private val smartChg by lazy {
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals("persist.vendor.smartchg")
            }
        }.map { it.getMethodInstance(classLoader) }.toList()
    }

    override fun init() = hasEnable("smart_discharge") {
        smartChg.createHooks {
            returnConstant(true)
        }
    }
}