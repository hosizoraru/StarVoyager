package star.sky.voyager.hook.hooks.settings

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.SettingsFeaturesCls

object NewNfcPage : HookRegister() {
    override fun init() = hasEnable("new_nfc_page") {
        SettingsFeaturesCls.methodFinder()
            .filterByName("isNeedShowMiuiNFC")
            .first().createHook {
                before {
                    it.result = true
                }
            }
    }
}