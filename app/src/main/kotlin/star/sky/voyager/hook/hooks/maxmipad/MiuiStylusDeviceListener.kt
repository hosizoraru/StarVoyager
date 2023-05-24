package star.sky.voyager.hook.hooks.maxmipad

import android.os.Build
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.callMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable

object MiuiStylusDeviceListener : HookRegister() {
    override fun init() = hasEnable("remove_stylus_bluetooth_restriction") {
        val isNew = Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU
        val clazzMiuiStylusDeviceListener =
            if (isNew) loadClass("com.miui.server.input.stylus.MiuiStylusDeviceListener")
            else loadClass("com.miui.server.stylus.MiuiStylusDeviceListener") // 目前是 A12
        clazzMiuiStylusDeviceListener.declaredConstructors.createHooks {
            after {
                setTouchModeStylusEnable(isNew)
            }
        }

        clazzMiuiStylusDeviceListener.declaredMethods.createHooks {
            after {
                setTouchModeStylusEnable(isNew)
            }
        }
    }

    private fun setTouchModeStylusEnable(isNew: Boolean) {
        val driverVersion =
            getString("remove_stylus_bluetooth_restriction_driver_version", "2")
                ?.toInt()
        val flag = if (isNew) (0x10 or driverVersion!!) else 1
        loadClass("miui.util.ITouchFeature").methodFinder().filterByName("getInstance").first()
            .invoke(null)?.objectHelper()
            ?.callMethod("setTouchMode", 0, 20, flag)
    }
}