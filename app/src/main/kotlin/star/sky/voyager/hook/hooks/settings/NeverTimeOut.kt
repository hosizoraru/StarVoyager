package star.sky.voyager.hook.hooks.settings

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullAs
import com.github.kyuubiran.ezxhelper.ObjectUtils.setObject
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object NeverTimeOut : HookRegister() {
    private var Oled: Boolean = false
    override fun init() = hasEnable("oled_never_time_out") {
        loadClass("com.android.settings.KeyguardTimeoutListPreference").methodFinder()
            .filterByName("disableUnusableTimeouts")
            .first().createHook {
                before { param ->
                    Oled =
                        getObjectOrNullAs(param.thisObject, "mIsOled")!!
                    setObject(param.thisObject, "mIsOled", false)
                }

                after { param ->
                    setObject(param.thisObject, "mIsOled", Oled)
                }
            }
    }
}