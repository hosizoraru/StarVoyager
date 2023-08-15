package star.sky.voyager.hook.hooks.aiasstvision

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.AiasstVisionSystemUtilsCls

object NewVoip : HookRegister() {
    override fun init() = hasEnable("aiasst_vision_new_voip") {
        AiasstVisionSystemUtilsCls.methodFinder()
            .filterByName("isSupportNewVoip")
            .first().createHook {
                returnConstant(true)
            }

    }
}