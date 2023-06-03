package star.sky.voyager.hook.hooks.systemui

import android.content.Context
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object DisableBluetooth : HookRegister() {
    override fun init() = hasEnable("Disable_Bluetooth") {
        loadClass("com.android.settingslib.bluetooth.LocalBluetoothAdapter").methodFinder()
            .filterByName("isSupportBluetoothRestrict")
            .filterByParamTypes(Context::class.java)
            .first().createHook {
                before {
                    it.result = false
                }
            }
    }
}