package star.sky.voyager.hook.hooks.aiasstvision

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.AiasstVisionSystemUtilsCls

object SplitScreenTranslate : HookRegister() {
    override fun init() = hasEnable("split_screen_translate") {
        AiasstVisionSystemUtilsCls.methodFinder()
            .filterByName("isSupportSplitScreenTranslateDevice")
            .firstOrNull()?.createHook {
                returnConstant(true)
            }
    }
}