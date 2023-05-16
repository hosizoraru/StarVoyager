package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object OptimizeUnlockAnim : HookRegister() {
    override fun init() = hasEnable("optimize_unlock_anim") {
        loadClass("com.miui.home.launcher.compat.UserPresentAnimationCompatV12Phone").methodFinder()
            .first {
                name == "getSpringAnimator" && parameterCount == 6
            }.createHook {
            before {
                it.args[4] = 0.5f
                it.args[5] = 0.5f
            }
        }
    }
}