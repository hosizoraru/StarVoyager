package star.sky.voyager.hook.hooks.aicr

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object Attr : HookRegister() {
    override fun init() = hasEnable("privacy_mosaic") {
        loadClass("com.xiaomi.aicr.vision.VisionManager").methodFinder()
            .filterByName("isSupportAttr")
            .first().createHook {
                returnConstant(true)
            }
    }
}