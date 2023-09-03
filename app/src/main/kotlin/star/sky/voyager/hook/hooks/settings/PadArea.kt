package star.sky.voyager.hook.hooks.settings

import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.SettingsFeaturesCls

object PadArea : HookRegister() {
    override fun init() = hasEnable("pad_area") {
        setStaticObject(
            SettingsFeaturesCls,
            "IS_SUPPORT_TABLET_SCREEN_SETTINGS",
            true
        )
    }
}