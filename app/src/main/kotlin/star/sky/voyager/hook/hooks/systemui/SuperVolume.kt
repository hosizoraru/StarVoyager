package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.PluginClassLoader.hookPluginClassLoader

object SuperVolume : HookRegister() {
    override fun init() = hasEnable("super_volume") {
        hookPluginClassLoader { _, classLoader ->
            loadClass(
                "miui.systemui.util.CommonUtils",
                classLoader
            ).methodFinder().filter {
                name in setOf(
                    "supportSuperVolume",
                    "voiceSupportSuperVolume",
                )
            }.toList().createHooks {
                returnConstant(true)
            }
        }
    }
}