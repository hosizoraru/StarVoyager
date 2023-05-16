package star.sky.voyager.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils
import star.sky.voyager.utils.key.hasEnable

object SystemPropertiesHook : HookRegister() {
    override fun init() = hasEnable("media_volume_steps_switch") {
        val mediaSteps = XSPUtils.getInt("media_volume_steps", 15)
        loadClass("android.os.SystemProperties").methodFinder().first {
            name == "getInt" && returnType == Int::class.java
        }.createHook {
            before {
                when (it.args[0] as String) {
                    "ro.config.media_vol_steps" -> it.result = mediaSteps
                }
            }
        }
    }
}