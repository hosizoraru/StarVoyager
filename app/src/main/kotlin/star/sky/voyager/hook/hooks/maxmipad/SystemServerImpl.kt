package star.sky.voyager.hook.hooks.maxmipad

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object SystemServerImpl : HookRegister() {
    override fun init() = hasEnable("no_magic_pointer") {
        loadClass("com.android.server.SystemServerImpl").methodFinder().first {
            name == "addMagicPointerManagerService"
        }.createHook {
            returnConstant(null)
        }
    }
}