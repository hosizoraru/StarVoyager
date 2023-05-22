package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.getObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object IconCorner : HookRegister() {
    override fun init() = hasEnable("icon_corner") {
        loadClass("com.miui.home.launcher.bigicon.BigIconUtil").methodFinder().first {
            name == "getCroppedFromCorner" && parameterCount == 4
        }.createHook {
            before {
                it.args[0] = 2
                it.args[1] = 2
            }
        }

        val MaMlHostViewClass = loadClass("com.miui.home.launcher.maml.MaMlHostView")
        MaMlHostViewClass.methodFinder().first {
            name == "getCornerRadius"
        }.createHook {
            before {
                it.result = it.thisObject.getObjectField("mEnforcedCornerRadius") as Float
            }
        }

        MaMlHostViewClass.methodFinder().first {
            name == "computeRoundedCornerRadius" && parameterCount == 1
        }.createHook {
            before {
                it.result = it.thisObject.getObjectField("mEnforcedCornerRadius") as Float
            }
        }

        loadClass("com.miui.home.launcher.LauncherAppWidgetHostView").methodFinder().first {
            name == "computeRoundedCornerRadius" && parameterCount == 1
        }.createHook {
            before {
                it.result = it.thisObject.getObjectField("mEnforcedCornerRadius") as Float
            }
        }
    }
}