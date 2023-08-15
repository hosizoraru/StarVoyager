package star.sky.voyager.hook.hooks.settings

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.SettingsFeaturesCls
import star.sky.voyager.utils.yife.Build.IS_INTERNATIONAL_BUILD

object NoveltyHaptic : HookRegister() {
    override fun init() = hasEnable("novelty_haptic") {
        when (IS_INTERNATIONAL_BUILD) {
            true -> return@hasEnable
            false -> {
                SettingsFeaturesCls.methodFinder()
                    .filterByName("isNoveltyHaptic")
                    .first().createHook {
                        returnConstant(true)
                    }
            }
        }
    }
}