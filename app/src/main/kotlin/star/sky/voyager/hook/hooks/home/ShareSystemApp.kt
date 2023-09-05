package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XC_MethodHook.Unhook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object ShareSystemApp : HookRegister() {
    private var hook1: Unhook? = null
    override fun init() = hasEnable("share_system_app") {
        val zCls =
            loadClass("com.miui.home.launcher.shortcuts.SystemShortcutMenuItem\$ShareAppShortcutMenuItem")
        val utilsCls =
            loadClass("com.miui.home.launcher.common.Utilities")
        val isSystemPackage =
            utilsCls.methodFinder().filterByName("isSystemPackage").first()

        zCls.methodFinder().filterByName("isValid")
            .first().createHook {
                before {
                    hook1 = isSystemPackage.createHook {
                        returnConstant(false)
                    }
                }

                after {
                    hook1?.unhook()
                }
            }
    }
}