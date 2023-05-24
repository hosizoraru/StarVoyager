package star.sky.voyager.hook.hooks.maxmipad

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object MiuiMagicPointerUtils : HookRegister() {
    override fun init() = hasEnable("no_magic_pointer") {
        // 老版本系统hook的，新版本已经找不到此Class 关闭Magic Pointer
        val MagicPointerClass =
            loadClassOrNull("android.magicpointer.util.MiuiMagicPointerUtils", classLoader)
        MagicPointerClass?.methodFinder()?.first {
            name == "isEnable"
        }?.createHook {
            returnConstant(false)
        }
    }
}