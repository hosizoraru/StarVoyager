package star.sky.voyager.hook.hooks.screenrecorder

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.MemberExtensions.paramCount
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object EnablePlaybackCapture : HookRegister() {
    override fun init() = hasEnable("force_enable_native_playback_capture") {
        ClassUtils.loadClass("android.os.SystemProperties").methodFinder().first {
            name == "getBoolean"
                    && paramCount == 2
                    && parameterTypes[0] == String::class.java
                    && parameterTypes[1] == Boolean::class.java
        }.createHook {
            before { param ->
                if (param.args[0] == "ro.vendor.audio.playbackcapture.screen") {
                    param.result = true
                }
            }
        }
    }
}