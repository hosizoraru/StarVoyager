package star.sky.voyager.hook.hooks.maxmipad

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.MemberExtensions.paramCount
import com.github.kyuubiran.ezxhelper.finders.ConstructorFinder.`-Static`.constructorFinder
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.invokeMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.XSharedPreferences

object MiuiStylusDeviceListener : HookRegister() {
    override fun init() = hasEnable("remove_stylus_bluetooth_restriction") {
        val driverVersion =
            XSharedPreferences.getString("remove_stylus_bluetooth_restriction_driver_version", "2")
                .toInt()
        loadClass("com.miui.server.input.stylus.MiuiStylusDeviceListener").constructorFinder()
            .first {
                true
            }.createHook {
            after {
                val ITouchFeature = loadClass("miui.util.ITouchFeature")
                val mTouchFeature = ITouchFeature.methodFinder().first {
                    name == "getInstance"
                }.invoke(null)
                mTouchFeature?.invokeMethod(0, 20, 0x10 or driverVersion) {
                    name == "setTouchMode" && paramCount == 3
                }
            }
        }
        loadClass("com.miui.server.input.stylus.MiuiStylusDeviceListener").methodFinder().first {
            true
        }.createHook {
            replace {
                val ITouchFeature = loadClass("miui.util.ITouchFeature")
                val mTouchFeature = ITouchFeature.methodFinder().first {
                    name == "getInstance"
                }.invoke(null)
                mTouchFeature?.invokeMethod(0, 20, 0x10 or driverVersion) {
                    name == "setTouchMode" && paramCount == 3
                }
            }
        }
    }
}