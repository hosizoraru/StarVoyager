package star.sky.voyager.hook.hooks.systemui

import star.sky.voyager.utils.api.findClass
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.PluginClassLoader

object HyperOSMIUI : HookRegister() {
    override fun init() = hasEnable("hyperos_miui") {
        PluginClassLoader.hookPluginClassLoader { _, classLoader ->
            "miui.systemui.util.CommonUtils".findClass(classLoader)
                .getDeclaredField("IS_BELOW_MIUI_15")
                .apply {
                    isAccessible = true
                    setBoolean(null, false)
                }
        }
    }
}