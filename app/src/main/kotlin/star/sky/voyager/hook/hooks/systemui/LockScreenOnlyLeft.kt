package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object LockScreenOnlyLeft : HookRegister() {
    override fun init() = hasEnable("lock_screen_only_left") {
        val leftController =
            loadClass("com.android.keyguard.KeyguardMoveLeftController")
        val moveHelper =
            loadClass("com.android.keyguard.KeyguardMoveHelper")

        leftController.methodFinder().filter {
            name in setOf("isLeftViewLaunchActivity", "onTouchMove")
        }.toList().createHooks {
            returnConstant(false)
        }

        leftController.methodFinder().filterByName("reset")
            .first().createHook {
                returnConstant(null)
            }

        moveHelper.methodFinder().filter {
            name in setOf("isMovingLeftView")
        }.toList().createHooks {
            returnConstant(false)
        }
    }
}