package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.isPad
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object OptimizeUnlockAnim : HookRegister() {
    override fun init() = hasEnable("optimize_unlock_anim") {
        loadClass(
            when {
                !isPad() -> "com.miui.home.launcher.compat.UserPresentAnimationCompatV12Phone"
                else -> "com.miui.home.launcher.compat.UserPresentAnimationCompatV12Spring"
            }
        ).methodFinder()
            .filterByName("getSpringAnimator")
            .filterByParamCount(6)
            .first()
            .createHook {
                before {
                    it.args[4] = 0.5f
                    it.args[5] = 0.5f
                }
            }
    }
}