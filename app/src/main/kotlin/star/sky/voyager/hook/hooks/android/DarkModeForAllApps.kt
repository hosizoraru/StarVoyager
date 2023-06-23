package star.sky.voyager.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.Build.IS_INTERNATIONAL_BUILD

object DarkModeForAllApps : HookRegister() {
    override fun init() = hasEnable("dark_mode_for_all_apps") {
        if (IS_INTERNATIONAL_BUILD) return@hasEnable
        loadClass("com.android.server.ForceDarkAppListManager").methodFinder()
            .filterByName("getDarkModeAppList")
            .toList().createHooks {
                before {
                    setStaticObject(
                        loadClass("miui.os.Build"),
                        "IS_INTERNATIONAL_BUILD",
                        true
                    )
                }

                after {
                    setStaticObject(
                        loadClass("miui.os.Build"),
                        "IS_INTERNATIONAL_BUILD",
                        IS_INTERNATIONAL_BUILD
                    )
                }
            }
    }
}