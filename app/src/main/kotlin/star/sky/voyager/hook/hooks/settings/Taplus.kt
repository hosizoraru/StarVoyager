package star.sky.voyager.hook.hooks.settings

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.SettingsFeatures.SettingsFeaturesCls

object Taplus : HookRegister() {
    override fun init() = hasEnable("unlock_taplus") {
        SettingsFeaturesCls.methodFinder()
            .filterByName("isNeedRemoveContentExtension")
            .first().createHook {
                returnConstant(false)
            }
    }
}