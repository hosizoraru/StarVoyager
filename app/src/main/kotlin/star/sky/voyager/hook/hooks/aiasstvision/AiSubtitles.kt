package star.sky.voyager.hook.hooks.aiasstvision

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.SupportAiSubtitlesUtils

object AiSubtitles : HookRegister() {
    override fun init() = hasEnable("ai_subtitles") {
        SupportAiSubtitlesUtils.methodFinder().filter {
            name in setOf(
                "isSupportAiSubtitles",
                "isSupportJapanKoreaTranslation",
            )
        }.toList().createHooks {
            returnConstant(true)
        }
    }
}