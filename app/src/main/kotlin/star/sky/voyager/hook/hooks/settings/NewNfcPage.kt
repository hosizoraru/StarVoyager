package star.sky.voyager.hook.hooks.settings

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object NewNfcPage : HookRegister() {
    override fun init() = hasEnable("new_nfc_page") {
        loadClass("com.android.settings.utils.SettingsFeatures").methodFinder()
            .filterByName("isNeedShowMiuiNFC")
            .first().createHook {
                before {
                    it.result = true
                }
            }
    }
}