package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XC_MethodHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable

object FoldDeviceDock : HookRegister() {
    override fun init() = hasEnable("home_fold_dock") {
        var hook1: XC_MethodHook.Unhook? = null
        var hook2: XC_MethodHook.Unhook? = null

        val hotSeatsCls = loadClass("com.miui.home.launcher.hotseats.HotSeats")
        val deviceConfigCls = loadClass("com.miui.home.launcher.DeviceConfig")
        val applicationCls = loadClass("com.miui.home.launcher.Application")
        val foldDockHotSeat = getInt("fold_dock_hot_seating", 3)
        val foldDockRun = getInt("fold_dock_running", 2)

        hotSeatsCls.methodFinder()
            .filterByName("initContent")
            .first().createHook {
                before {
                    hook1 = deviceConfigCls.methodFinder()
                        .filterByName("isFoldDevice")
                        .first().createHook {
                            before {
                                it.result = true
                            }
                        }
                }
                after {
                    hook1?.unhook()
                }
            }

        hotSeatsCls.methodFinder().filter {
            name in setOf("updateContent", "isNeedUpdateItemInfo")
        }.toList().createHooks {
            before {
                hook2 = applicationCls.methodFinder()
                    .filterByName("isInFoldLargeScreen")
                    .first().createHook {
                        before {
                            it.result = true
                        }
                    }
            }
            after {
                hook2?.unhook()
            }
        }

        loadClass("com.miui.home.launcher.hotseats.HotSeatsListRecentsAppProvider\$2").methodFinder()
            .filterByName("handleMessage")
            .first().createHook {
                before {
                    hook2 = applicationCls.methodFinder()
                        .filterByName("isInFoldLargeScreen")
                        .first().createHook {
                            before {
                                it.result = true
                            }
                        }
                }
                after {
                    hook2?.unhook()
                }
            }

        deviceConfigCls.methodFinder()
            .filterByName("getHotseatMaxCount")
            .first().createHook {
                after {
                    it.result = foldDockHotSeat
                }
            }

        loadClass("com.miui.home.launcher.hotseats.HotSeatsListRecentsAppProvider").methodFinder()
            .filterByName("getLimitCount")
            .first().createHook {
                before {
                    it.result = foldDockRun
                }
            }

        setOf(
            loadClass("com.miui.home.launcher.allapps.LauncherMode"),
            deviceConfigCls
        ).forEach { cls ->
            cls.methodFinder()
                .filterByName("isHomeSupportSearchBar")
                .first().createHook {
                    before {
                        it.result = false
                    }
                }
        }
    }
}

//        try {
//            hotSeatsCls.methodFinder()
//                .filterByName("updateContent")
//                .first()
//        } catch (e: Exception) {
//            hotSeatsCls.methodFinder()
//                .filterByName("updateContentView")
//                .first()
//        }.createHook {
//            before {
//                hook2 = applicationCls.methodFinder()
//                    .filterByName("isInFoldLargeScreen")
//                    .first().createHook {
//                        before {
//                            it.result = true
//                        }
//                    }
//            }
//            after {
//                hook2?.unhook()
//            }
//        }

//        hotSeatsCls.methodFinder()
//            .filterByName("isNeedUpdateItemInfo")
//            .first().createHook {
//                before {
//                    hook2 = applicationCls.methodFinder()
//                        .filterByName("isInFoldLargeScreen")
//                        .first().createHook {
//                            before {
//                                it.result = true
//                            }
//                        }
//                }
//                after {
//                    hook2?.unhook()
//                }
//            }