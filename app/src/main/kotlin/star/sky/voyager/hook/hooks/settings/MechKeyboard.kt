package star.sky.voyager.hook.hooks.settings

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object MechKeyboard : HookRegister() {
    override fun init() = hasEnable("mech_keyboard") {
        loadClass("com.android.settings.inputmethod.InputMethodFunctionSelectUtils").methodFinder()
            .filter {
                name in setOf("isMechKeyboardUsable", "isSupportMechKeyboard")
            }.toList().createHooks {
                returnConstant(true)
            }
    }
}