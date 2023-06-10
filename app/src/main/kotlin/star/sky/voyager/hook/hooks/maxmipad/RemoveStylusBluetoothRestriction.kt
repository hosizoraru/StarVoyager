package star.sky.voyager.hook.hooks.maxmipad

import com.github.kyuubiran.ezxhelper.ClassUtils.invokeStaticMethodBestMatch
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable

object RemoveStylusBluetoothRestriction : HookRegister() {
    override fun init() = hasEnable("remove_stylus_bluetooth_restriction") {
        val clazzMiuiStylusDeviceListener =
            loadClass("com.miui.server.input.stylus.MiuiStylusDeviceListener")
        clazzMiuiStylusDeviceListener.declaredConstructors.createHooks {
            after {
                setTouchModeStylusEnable()
            }
        }
        clazzMiuiStylusDeviceListener.declaredMethods.createHooks {
            replace {
                setTouchModeStylusEnable()
            }
        }
    }

    private fun setTouchModeStylusEnable() {
        val driverVersion =
            getString("remove_stylus_bluetooth_restriction_driver_version", "2")?.toInt()
        val flag: Int = 0x10 or driverVersion!!
        val instanceITouchFeature =
            invokeStaticMethodBestMatch(
                loadClass("miui.util.ITouchFeature"),
                "getInstance"
            )!!
        invokeMethodBestMatch(
            instanceITouchFeature,
            "setTouchMode",
            null,
            0, 20, flag
        )
    }
}