package star.sky.voyager.hook.hooks.newmaxmipad

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object NoMagicPointer : HookRegister() {
    override fun init() = hasEnable("no_magic_pointer") {
        loadClassOrNull("android.magicpointer.util.MiuiMagicPointerUtils")
            ?.methodFinder()
            ?.filterByName("isEnable")
            ?.first()?.createHook {
                returnConstant(false)
            }
        loadClass("com.android.server.SystemServerImpl").methodFinder()
            .filterByName("addMagicPointerManagerService")
            .first().createHook {
                returnConstant(null)
            }
    }
}