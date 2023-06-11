package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.Build.IS_TABLET

object OptimizeUnlockAnim : HookRegister() {
    override fun init() = hasEnable("optimize_unlock_anim") {
        loadClass(
            when {
                IS_TABLET -> "com.miui.home.launcher.compat.UserPresentAnimationCompatV12Spring"
                else -> "com.miui.home.launcher.compat.UserPresentAnimationCompatV12Phone"
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