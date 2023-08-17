package star.sky.voyager.hook.hooks.aiasstvision

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object AiSubtitles : HookRegister() {
    override fun init() = hasEnable("ai_subtitles") {
        loadClass("com.xiaomi.aiasst.vision.utils.SupportAiSubtitlesUtils").methodFinder().filter {
            name in setOf(
                "isSupportAiSubtitles",
                "isSupportJapanKoreaTranslation",
            )
        }.toList().createHooks {
            returnConstant(true)
        }
    }
}