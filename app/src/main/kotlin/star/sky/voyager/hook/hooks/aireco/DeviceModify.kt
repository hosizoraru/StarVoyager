package star.sky.voyager.hook.hooks.aireco

import android.os.Build
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XposedHelpers
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object DeviceModify : HookRegister() {
    override fun init() = hasEnable("device_modify") {
        loadClass("com.xiaomi.aireco.utils.DeviceUtils").methodFinder()
            .filterByName("getVoiceAssistUserAgent").toList().createHooks {
                before {
                    XposedHelpers.setStaticObjectField(Build::class.java, "DEVICE", "nuwa")
                    XposedHelpers.setStaticObjectField(Build::class.java, "MODEL", "2210132C")
                    XposedHelpers.setStaticObjectField(Build::class.java, "MANUFACTURER", "Xiaomi")
                }
        }
    }
}