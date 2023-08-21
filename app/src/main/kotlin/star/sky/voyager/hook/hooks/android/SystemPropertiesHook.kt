package star.sky.voyager.hook.hooks.android

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.SystemProperties

object SystemPropertiesHook : HookRegister() {
    private val mediaSteps by lazy {
        getInt("media_volume_steps", 15)
    }

    override fun init() = hasEnable("media_volume_steps_switch") {
        SystemProperties.methodFinder()
            .filterByName("getInt")
            .filterByReturnType(Int::class.java)
            .first().createHook {
                before {
                    when (it.args[0] as String) {
                        "ro.config.media_vol_steps" -> it.result = mediaSteps
                    }
                }
            }
    }
}