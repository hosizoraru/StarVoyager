package star.sky.voyager.hook.hooks.android

import android.content.pm.ApplicationInfo
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.MiuiBuildCls
import star.sky.voyager.utils.yife.Build.IS_INTERNATIONAL_BUILD

object DarkModeForAllApps : HookRegister() {
    override fun init() = hasEnable("dark_mode_for_all_apps") {
        if (IS_INTERNATIONAL_BUILD) return@hasEnable
        val clazzForceDarkAppListManager =
            loadClass("com.android.server.ForceDarkAppListManager")
        clazzForceDarkAppListManager.methodFinder().filterByName("getDarkModeAppList").toList()
            .createHooks {
                before {
                    setStaticObject(MiuiBuildCls, "IS_INTERNATIONAL_BUILD", true)
                }
                after {
                    setStaticObject(
                        MiuiBuildCls,
                        "IS_INTERNATIONAL_BUILD",
                        IS_INTERNATIONAL_BUILD
                    )
                }
            }
        clazzForceDarkAppListManager.methodFinder().filterByName("shouldShowInSettings").toList()
            .createHooks {
                before { param ->
                    val info = param.args[0] as ApplicationInfo?
                    param.result =
                        !(info == null || (invokeMethodBestMatch(
                            info,
                            "isSystemApp"
                        ) as Boolean) || info.uid < 10000)
                }
            }
    }
}