package star.sky.voyager.hook.hooks.screenrecorder

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.SystemProperties

object EnablePlaybackCapture : HookRegister() {
    override fun init() = hasEnable("force_enable_native_playback_capture") {
        SystemProperties.methodFinder()
            .filterByName("getBoolean")
            .filterByParamCount(2)
            .filterByParamTypes(String::class.java, Boolean::class.java)
            .first().createHook {
                before { param ->
                    if (param.args[0] == "ro.vendor.audio.playbackcapture.screen") {
                        param.result = true
                    }
                }
            }
    }
}