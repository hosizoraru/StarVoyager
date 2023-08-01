package star.sky.voyager.hook.hooks.aiasstvision

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object MeetingMode : HookRegister() {
    override fun init() = hasEnable("meeting_mode") {
        val systemUtilsCls =
            loadClass("com.xiaomi.aiasst.vision.utils.SystemUtils")

        systemUtilsCls.methodFinder().filter {
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