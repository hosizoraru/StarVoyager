package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.voiceassistant.ChangeBrowserForMiAi
import star.sky.voyager.utils.init.AppRegister

object VoiceAssistant : AppRegister() {
    override val packageName: String = "com.miui.voiceassist"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            ChangeBrowserForMiAi, // 修改小爱同学使用的浏览器
        )
    }
}