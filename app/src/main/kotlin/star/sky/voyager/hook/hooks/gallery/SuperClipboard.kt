package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object SuperClipboard : HookRegister() {
    override fun init() = hasEnable("super_clipboard") {
        loadClass("com.miui.gallery.util.MiscUtil")
            .methodFinder().filterByName("isSupportSuperClipboard")
            .toList().createHooks {
                before { param ->
                    param.result = true
                }
            }
    }
}