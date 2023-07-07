package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object LockScreenRemoveLeftSide : HookRegister() {
    override fun init() = hasEnable("remove_the_left_side_of_the_lock_screen") {
        loadClass("com.android.keyguard.negative.MiuiKeyguardMoveLeftViewContainer").methodFinder()
            .filterByName("inflateLeftView")
            .first().createHook {
                before {
                    it.result = null
                }
            }
    }
}