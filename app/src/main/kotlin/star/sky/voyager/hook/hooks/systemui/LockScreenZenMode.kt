package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object LockScreenZenMode : HookRegister() {
    override fun init() = hasEnable("lock_screen_zen_mode") {
        loadClass("com.android.systemui.statusbar.notification.zen.ZenModeViewController").methodFinder()
            .filterByName("shouldBeVisible")
            .first().createHook {
                returnConstant(false)
            }
    }
}