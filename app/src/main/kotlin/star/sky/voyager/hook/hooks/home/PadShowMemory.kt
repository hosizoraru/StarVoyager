package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object PadShowMemory : HookRegister() {
    override fun init() = hasEnable("pad_show_memory") {
        loadClass("com.miui.home.recents.views.RecentsDecorations")
            .methodFinder().filter {
                name in setOf("canTxtMemInfoShow", "canTxtMemInfoShowIgnoreSmallWindow")
            }.toList().createHooks {
                returnConstant(true)
            }
    }
}