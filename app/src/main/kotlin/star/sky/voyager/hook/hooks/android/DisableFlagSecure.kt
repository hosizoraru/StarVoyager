package star.sky.voyager.hook.hooks.android

import android.content.Context
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.findField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object DisableFlagSecure : HookRegister() {
    override fun init() = hasEnable("disable_flag_secure") {
        loadClass("com.android.server.wm.WindowState").methodFinder().first {
            name == "isSecureLocked"
        }.createHook {
            before {
                it.result = false
            }
        }
        val WindowSurfaceControllerClass =
            loadClass("com.android.server.wm.WindowSurfaceController")

        WindowSurfaceControllerClass.methodFinder().first {
            name == "setSecure"
        }.createHook {
            before {
                it.args[0] = false
            }
        }

        WindowSurfaceControllerClass.constructors.createHooks {
            before {
                var flags = it.args[2] as Int
                val secureFlag = 128
                flags = flags and secureFlag.inv()
                it.args[2] = flags
            }
        }

        loadClass("com.android.server.wm.Task").methodFinder().first {
            name == "isResizeable"
        }.createHook {
            before {
                it.result = true
            }
        }

        val MiuiMultiWindowAdapterClass = loadClass("android.util.MiuiMultiWindowAdapter")

        MiuiMultiWindowAdapterClass.methodFinder().first {
            name == "getFreeformBlackList"
        }.createHook {
            after {
                it.result = (it.result as MutableList<*>).apply { clear() }
            }
        }

        MiuiMultiWindowAdapterClass.methodFinder().first {
            name == "getFreeformBlackListFromCloud" && parameterTypes[0] == Context::class.java
        }.createHook {
            after {
                it.result = (it.result as MutableList<*>).apply { clear() }
            }
        }

        val MiuiMultiWindowUtilsClass = loadClass("android.util.MiuiMultiWindowUtils")

        MiuiMultiWindowUtilsClass.methodFinder().first {
            name == "supportFreeform"
        }.createHook {
            after {
                it.result = true
            }
        }

        MiuiMultiWindowUtilsClass.methodFinder().first {
            name == "isForceResizeable"
        }.createHook {
            returnConstant(true)
        }

        val SettingsObserverClass =
            loadClass("com.android.server.wm.WindowManagerService\$SettingsObserver")

        SettingsObserverClass.methodFinder()
            .first {
                name == "onChange"
            }.createHook {
                after { param ->
                    val this0 =
                        param.thisObject.javaClass.findField("this\$0").get(param.thisObject)
                    val mAtmService = this0.javaClass.findField("mAtmService").get(this0)
                    mAtmService.javaClass.findField("mDevEnableNonResizableMultiWindow")
                        .setBoolean(mAtmService, true)
                }
            }

        SettingsObserverClass.methodFinder()
            .first {
                name == "updateDevEnableNonResizableMultiWindow"
            }.createHook {
                after { param ->
                    val this0 =
                        param.thisObject.javaClass.findField("this\$0").get(param.thisObject)
                    val mAtmService = this0.javaClass.findField("mAtmService").get(this0)
                    mAtmService.javaClass.findField("mDevEnableNonResizableMultiWindow")
                        .setBoolean(mAtmService, true)
                }
            }

        loadClass("com.android.server.wm.ActivityTaskManagerService").methodFinder().first {
            name == "retrieveSettings"
        }.createHook {
            after { param ->
                param.thisObject.javaClass.findField("mDevEnableNonResizableMultiWindow")
                    .setBoolean(param.thisObject, true)
            }
        }
    }
}