package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object OverlapMode : HookRegister() {
    override fun init() = hasEnable("Overlap_Mode") {
        loadClass("com.miui.home.launcher.overlay.assistant.AssistantDeviceAdapter").methodFinder()
            .filterByName("inOverlapMode")
            .first().createHook {
                returnConstant(true)
            }
    }
}