package star.sky.voyager.hook.hooks.settings

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.MiuiSettingsCls
import star.sky.voyager.utils.yife.Build.IS_GLOBAL_BUILD

object GoogleSettings : HookRegister() {
    override fun init() = hasEnable("google_settings") {
        MiuiSettingsCls.methodFinder()
            .filterByName("updateHeaderList")
            .first().createHook {
                after {
                    if (!IS_GLOBAL_BUILD) invokeMethodBestMatch(
                        it.thisObject,
                        "AddGoogleSettingsHeaders",
                        null,
                        it.args[0]
                    )
                }
            }
    }
}