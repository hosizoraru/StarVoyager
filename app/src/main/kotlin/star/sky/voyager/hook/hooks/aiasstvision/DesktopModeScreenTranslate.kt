package star.sky.voyager.hook.hooks.aiasstvision

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.AiasstVisionSystemUtilsCls

object DesktopModeScreenTranslate : HookRegister() {
    override fun init() = hasEnable("desktop_mode_screen_translate") {
        AiasstVisionSystemUtilsCls.methodFinder()
            .filterByName("isDesktopModeOpen")
            .first().createHook {
                returnConstant(false)
            }
    }
}