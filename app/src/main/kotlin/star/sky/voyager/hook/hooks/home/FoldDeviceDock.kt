package star.sky.voyager.hook.hooks.home

import android.content.Context
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XC_MethodHook
import star.sky.voyager.utils.api.hookAfterMethod
import star.sky.voyager.utils.api.hookBeforeMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable

object FoldDeviceDock : HookRegister() {
    override fun init() = hasEnable("home_fold_dock") {
        var hook1: XC_MethodHook.Unhook? = null
        var hook2: XC_MethodHook.Unhook? = null
        var hook3: XC_MethodHook.Unhook? = null

        val hotSeatsClass = loadClass("com.miui.home.launcher.hotseats.HotSeats")
        hotSeatsClass.methodFinder()
            .filterByName("initContent")
            .first().createHook {
                before {
                    hook1 = "com.miui.home.launcher.DeviceConfig".hookBeforeMethod(
                        classLoader,
                        "isFoldDevice"
                    ) { hookParam ->
                        hookParam.result = true
                    }
                }
                after {
                    hook1?.unhook()
                }
            }

        try {
            hotSeatsClass.methodFinder()
                .filterByName("updateContent")
                .first()
        } catch (e: Exception) {
            hotSeatsClass.methodFinder()
                .filterByName("updateContentView")
                .first()
        }.createHook {
            before {
                hook2 = "com.miui.home.launcher.Application".hookBeforeMethod(
                    classLoader,
                    "isInFoldLargeScreen"
                ) { hookParam ->
                    hookParam.result = true
                }
            }
            after {
                hook2?.unhook()
            }
        }

        hotSeatsClass.methodFinder()
            .filterByName("isNeedUpdateItemInfo")
            .first().createHook {
                before {
                    hook3 = "com.miui.home.launcher.Application".hookBeforeMethod(
                        classLoader,
                        "isInFoldLargeScreen"
                    ) { hookParam -> hookParam.result = true }
                }
                after {
                    hook3?.unhook()
                }
            }

        "com.miui.home.launcher.DeviceConfig".hookAfterMethod(
            classLoader,
            "getHotseatMaxCount"
        ) {
            it.result = getInt("fold_dock_hot_seat", 3)
        }

        "com.miui.home.launcher.hotseats.HotSeatsListRecentsAppProvider".hookBeforeMethod(
            classLoader,
            "getLimitCount"
        ) {
            it.result = getInt("fold_dock_run", 2)
        }

        "com.miui.home.launcher.allapps.LauncherMode".hookBeforeMethod(
            classLoader,
            "isHomeSupportSearchBar",
            Context::class.java
        ) {
            it.result = false
        }
    }
}