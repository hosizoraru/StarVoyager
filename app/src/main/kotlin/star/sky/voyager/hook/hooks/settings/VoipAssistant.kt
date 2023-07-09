package star.sky.voyager.hook.hooks.settings

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object VoipAssistant : HookRegister() {
    override fun init() = hasEnable("voip_assistant") {
        loadClass("com.android.settings.lab.MiuiVoipAssistantController").methodFinder()
            .filterByName("isNotSupported")
            .first().createHook {
                returnConstant(false)
            }
    }
}