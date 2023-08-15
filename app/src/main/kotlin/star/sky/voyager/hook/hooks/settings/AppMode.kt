package star.sky.voyager.hook.hooks.settings

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.voyager.LazyClass.SettingsFeaturesCls
import star.sky.voyager.utils.yife.Build.IS_TABLET

object AppMode : HookRegister() {
    override fun init() {
        if (IS_TABLET) return
        SettingsFeaturesCls.methodFinder()
            .filterByName("isShowApplicationMode")
            .first().createHook {
                returnConstant(true)
            }
    }
}