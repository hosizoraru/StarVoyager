package star.sky.voyager.hook.hooks.aiasstvision

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.SupportAiSubtitlesUtils

object OfflineAiSubtitles : HookRegister() {
    override fun init() = hasEnable("offline_ai_subtitles") {
        SupportAiSubtitlesUtils.methodFinder()
            .filterByName("isSupportOfflineAiSubtitles")
            .first().createHook {
                returnConstant(true)
            }
    }
}