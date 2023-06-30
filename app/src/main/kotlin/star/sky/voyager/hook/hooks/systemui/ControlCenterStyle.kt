package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.setObject
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object ControlCenterStyle : HookRegister() {
    override fun init() = hasEnable("control_center_style") {
        loadClass("com.android.systemui.controlcenter.policy.ControlCenterControllerImpl").declaredConstructors.createHooks {
            after {
                setObject(it.thisObject, "forceUseControlCenterPanel", false)
            }
        }
        loadClass("com.miui.systemui.SettingsObserver").methodFinder()
            .filterByName("setValue\$default").first()
            .createHook {
                before {
                    if (it.args[1] == "force_use_control_panel") {
                        it.args[2] = 0
                    }
                }
            }
    }
}