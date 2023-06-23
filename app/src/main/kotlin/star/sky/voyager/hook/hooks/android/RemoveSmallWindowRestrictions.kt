package star.sky.voyager.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.field

object RemoveSmallWindowRestrictions : HookRegister() {
    override fun init() = hasEnable("remove_small_window_restrictions") {
        val activityTaskManagerService =
            loadClass("com.android.server.wm.ActivityTaskManagerService")
        val settingsObserver =
            loadClass("com.android.server.wm.WindowManagerService\$SettingsObserver")
        val miuiMultiWindowUtils =
            loadClass("android.util.MiuiMultiWindowUtils")
        val wmTask =
            loadClass("com.android.server.wm.Task")
        val miuiMultiWindowAdapter =
            loadClass("android.util.MiuiMultiWindowAdapter")

        activityTaskManagerService.methodFinder()
            .filterByName("retrieveSettings")
            .toList().createHooks {
                after { param ->
                    param.thisObject.javaClass.field("mDevEnableNonResizableMultiWindow")
                        .setBoolean(param.thisObject, true)
                }
            }
        settingsObserver.methodFinder()
            .filterByName("updateDevEnableNonResizableMultiWindow")
            .toList().createHooks {
                after { param ->
                    val this0 = param.thisObject.javaClass.field("this\$0")
                        .get(param.thisObject)
                    val mAtmService = this0.javaClass.field("mAtmService")
                        .get(this0)
                    mAtmService.javaClass.field("mDevEnableNonResizableMultiWindow")
                        .setBoolean(mAtmService, true)
                }
            }
        settingsObserver.methodFinder()
            .filterByName("onChange")
            .toList().createHooks {
                after { param ->
                    val this0 = param.thisObject.javaClass.field("this\$0")
                        .get(param.thisObject)
                    val mAtmService = this0.javaClass.field("mAtmService")
                        .get(this0)
                    mAtmService.javaClass.field("mDevEnableNonResizableMultiWindow")
                        .setBoolean(mAtmService, true)
                }
            }

        miuiMultiWindowUtils.methodFinder().filter {
            name in setOf("isForceResizeable", "supportFreeform")
        }.toList().createHooks {
            returnConstant(true)
        }

        wmTask.methodFinder()
            .filterByName("isResizeable")
            .first().createHook {
                returnConstant(true)
            }

        miuiMultiWindowAdapter.methodFinder().first {
            name in setOf(
                "getFreeformBlackList",
                "getFreeformBlackListFromCloud",
                "getStartFromFreeformBlackListFromCloud"
            )
        }.createHook {
            returnConstant(mutableListOf<String>())
        }
    }
}