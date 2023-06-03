package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.getObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object IconCorner : HookRegister() {
    override fun init() = hasEnable("icon_corner") {
        loadClass("com.miui.home.launcher.bigicon.BigIconUtil").methodFinder()
            .filterByName("getCroppedFromCorner")
            .filterByParamCount(4)
            .first().createHook {
                before {
                    it.args[0] = 2
                    it.args[1] = 2
                }
            }

        val maMlHostViewClass = loadClass("com.miui.home.launcher.maml.MaMlHostView")
        maMlHostViewClass.methodFinder()
            .filterByName("getCornerRadius")
            .first().createHook {
                before {
                    it.result = it.thisObject.getObjectField("mEnforcedCornerRadius") as Float
                }
            }

        maMlHostViewClass.methodFinder()
            .filterByName("computeRoundedCornerRadius")
            .filterByParamCount(1)
            .first().createHook {
                before {
                    it.result = it.thisObject.getObjectField("mEnforcedCornerRadius") as Float
                }
            }

        loadClass("com.miui.home.launcher.LauncherAppWidgetHostView").methodFinder()
            .filterByName("computeRoundedCornerRadius")
            .filterByParamCount(1)
            .first().createHook {
                before {
                    it.result = it.thisObject.getObjectField("mEnforcedCornerRadius") as Float
                }
            }
    }
}