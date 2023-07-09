package star.sky.voyager.hook.hooks.settings

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object ImeBottom : HookRegister() {
    override fun init() = hasEnable("ime_bottom") {
        ClassUtils.loadClass("com.android.settings.inputmethod.InputMethodFunctionSelectUtils")
            .methodFinder()
            .filterByName("isMiuiImeBottomSupport")
            .first().createHook {
                returnConstant(true)
            }
    }
}