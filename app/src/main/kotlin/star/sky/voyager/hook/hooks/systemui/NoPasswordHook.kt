package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object NoPasswordHook : HookRegister() {
    override fun init() = hasEnable("no_need_to_enter_password_when_power_on") {
        loadClass("com.android.internal.widget.LockPatternUtils\$StrongAuthTracker").methodFinder()
            .filterByName("isBiometricAllowedForUser")
            .first().createHook {
                before {
                    it.result = true
                }
            }
        loadClass("com.android.internal.widget.LockPatternUtils").methodFinder()
            .filterByName("isBiometricAllowedForUser")
            .first().createHook {
                before {
                    it.result = true
                }
            }
    }
}