package star.sky.voyager.hook.hooks.gallery

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object OpenVideoSelf : HookRegister() {
    override fun init() = hasEnable("open_video_self") {
        loadClass("com.miui.gallery.util.VideoPlayerCompat").methodFinder()
            .filterByName("getMiuiVideoPackageName")
            .first().createHook {
                returnConstant("com.miui.gallery")
            }
    }
}