package star.sky.voyager.hook.hooks.maxmipad

import com.github.kyuubiran.ezxhelper.ClassUtils.invokeStaticMethodBestMatch
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.XSharedPreferences

object MiuiStylusDeviceListener : HookRegister() {
    override fun init() = hasEnable("remove_stylus_bluetooth_restriction") {
        val driverVersion =
            XSharedPreferences.getString("remove_stylus_bluetooth_restriction_driver_version", "2")
                .toInt()
        val MiuiStylusDeviceListenerClass =
            loadClass("com.miui.server.input.stylus.MiuiStylusDeviceListener")
        MiuiStylusDeviceListenerClass.constructors.createHooks {
            after {
                invokeStaticMethodBestMatch(
                    loadClass("miui.util.ITouchFeature"),
                    "getInstance"
                )?.let {
                    invokeMethodBestMatch(it, "setTouchMode", null, 0, 20, 0x10 or driverVersion)
                }
            }
        }
        MiuiStylusDeviceListenerClass.methodFinder().filter {
            true
        }.toList().createHooks {
            replace {
                invokeStaticMethodBestMatch(
                    loadClass("miui.util.ITouchFeature"),
                    "getInstance"
                )?.let {
                    invokeMethodBestMatch(it, "setTouchMode", null, 0, 20, 0x10 or driverVersion)
                }
            }
        }
    }
}