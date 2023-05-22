package star.sky.voyager.hook.hooks.aireco

import android.os.Build
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object DeviceModify : HookRegister() {
    override fun init() = hasEnable("device_modify") {
        loadClass("com.xiaomi.aireco.utils.DeviceUtils").methodFinder()
            .filterByName("getVoiceAssistUserAgent").toList().createHooks {
                before {
                    setStaticObject(Build::class.java, "DEVICE", "nuwa")
                    setStaticObject(Build::class.java, "MODEL", "2210132C")
                    setStaticObject(Build::class.java, "MANUFACTURER", "Xiaomi")
                }
            }
    }
}