package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.callMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object ShortcutItemCount : HookRegister() {
    override fun init() = hasEnable("shortcut_remove_restrictions") {
        loadClass("com.miui.home.launcher.shortcuts.AppShortcutMenu").methodFinder().first {
            name == "getMaxCountInCurrentOrientation"
        }.createHook {
            after {
                it.result = 20
            }
        }

        loadClass("com.miui.home.launcher.shortcuts.AppShortcutMenu").methodFinder().first {
            name == "getMaxShortcutItemCount"
        }.createHook {
            after {
                it.result = 20
            }
        }

        loadClass("com.miui.home.launcher.shortcuts.AppShortcutMenu").methodFinder().first {
            name == "getMaxVisualHeight"
        }.createHook {
            after {
                it.result = it.thisObject.callMethod("getItemHeight")
            }
        }
    }
}