package star.sky.voyager.hook.hooks.aiasstvision

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.AiasstVisionSystemUtilsCls

object MeetingMode : HookRegister() {
    override fun init() = hasEnable("meeting_mode") {
        AiasstVisionSystemUtilsCls.methodFinder().filter {
            name in setOf(
                "isSupportNewVoip",
                "isM81",
                "isM82",
            )
        }.toList().createHooks {
            returnConstant(true)
        }
    }
}