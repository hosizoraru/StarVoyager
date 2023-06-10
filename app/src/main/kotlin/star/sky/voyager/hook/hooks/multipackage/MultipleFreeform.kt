package star.sky.voyager.hook.hooks.multipackage

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object MultipleFreeform : HookRegister() {
    override fun init() = hasEnable("") {
        when (hostPackageName) {
            "com.android.settings" -> {
                val freeformGuideSettings =
                    loadClass("com.android.settings.freeform.FreeformGuideSettings")
                freeformGuideSettings.methodFinder()
                    .filterByName("isSupportMultipleAndFreeDrag")
                    .first().createHook {
                        returnConstant(true)
                    }
            }

            "com.miui.home" -> {
                val smallWindowShortcutMenuItem =
                    loadClass("com.miui.home.launcher.shortcuts.SystemShortcutMenuItem\$SmallWindowShortcutMenuItem")
                smallWindowShortcutMenuItem.methodFinder()
                    .filterByName("isValid")
                    .first().createHook {
                        returnConstant(true)
                    }
            }
        }
    }
}