package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable

object AnimDurationRatio : HookRegister() {
    private val value1 by lazy {
        getInt("home_anim_ratio", 100).toFloat() / 100f
    }

    private val value2 by lazy {
        getInt("home_anim_ratio_recent", 100).toFloat() / 100f
    }

    override fun init() = hasEnable("home_anim_ratio_binding") {
        loadClass("com.miui.home.recents.util.RectFSpringAnim").methodFinder()
            .filterByName("getModifyResponse")
            .first().createHook {
                before {
                    it.result = it.args[0] as Float * value1
                }
            }

        loadClass("com.miui.home.launcher.common.DeviceLevelUtils").methodFinder()
            .filterByName("getDeviceLevelTransitionAnimRatio")
            .first().createHook {
                before {
                    it.result = value2
                }
            }
    }
}