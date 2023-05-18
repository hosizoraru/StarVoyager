package star.sky.voyager.hook.hooks.maxmipad

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object MiuiMagicPointerUtils : HookRegister() {
    override fun init() = hasEnable("no_magic_pointer") {
        loadClass("android.magicpointer.util.MiuiMagicPointerUtils").methodFinder().first {
            name == "isEnable"
        }.createHook {
            returnConstant(false)
        }
    }
}