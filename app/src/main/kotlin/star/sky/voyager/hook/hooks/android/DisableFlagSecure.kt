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
        val WindowSurfaceControllerClass =
            loadClass("com.android.server.wm.WindowSurfaceController")
        val MiuiMultiWindowAdapterClass =
            loadClass("android.util.MiuiMultiWindowAdapter")
        val MiuiMultiWindowUtilsClass =
            loadClass("android.util.MiuiMultiWindowUtils")
        val SettingsObserverClass =
            loadClass("com.android.server.wm.WindowManagerService\$SettingsObserver")

        loadClass("com.android.server.wm.WindowState").methodFinder()
            .filterByName("isSecureLocked")
            .first().createHook {
                before {
                    it.result = false
                }
            }

        WindowSurfaceControllerClass.methodFinder()
            .filterByName("setSecure")
            .first().createHook {
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

        loadClass("com.android.server.wm.Task").methodFinder()
            .filterByName("isResizeable")
            .first().createHook {
                before {
                    it.result = true
                }
            }

        MiuiMultiWindowAdapterClass.methodFinder()
            .filterByName("getFreeformBlackList")
            .first().createHook {
                after {
                    it.result = (it.result as MutableList<*>).apply { clear() }
                }
            }

        MiuiMultiWindowAdapterClass.methodFinder()
            .filterByName("getFreeformBlackListFromCloud")
            .filterByParamTypes(Context::class.java)
            .first().createHook {
                after {
                    it.result = (it.result as MutableList<*>).apply { clear() }
                }
            }

        MiuiMultiWindowUtilsClass.methodFinder()
            .filterByName("supportFreeform")
            .first().createHook {
                after {
                    it.result = true
                }
            }

        MiuiMultiWindowUtilsClass.methodFinder()
            .filterByName("isForceResizeable")
            .first().createHook {
                returnConstant(true)
            }

        SettingsObserverClass.methodFinder()
            .filterByName("onChange")
            .first().createHook {
                after { param ->
                    val this0 =
                        param.thisObject.javaClass.findField("this\$0").get(param.thisObject)
                    val mAtmService = this0.javaClass.findField("mAtmService").get(this0)
                    mAtmService.javaClass.findField("mDevEnableNonResizableMultiWindow")
                        .setBoolean(mAtmService, true)
                }
            }

        SettingsObserverClass.methodFinder()
            .filterByName("updateDevEnableNonResizableMultiWindow")
            .first().createHook {
                after { param ->
                    val this0 =
                        param.thisObject.javaClass.findField("this\$0").get(param.thisObject)
                    val mAtmService = this0.javaClass.findField("mAtmService").get(this0)
                    mAtmService.javaClass.findField("mDevEnableNonResizableMultiWindow")
                        .setBoolean(mAtmService, true)
                }
            }

        loadClass("com.android.server.wm.ActivityTaskManagerService").methodFinder()
            .filterByName("retrieveSettings")
            .first().createHook {
                after { param ->
                    param.thisObject.javaClass.findField("mDevEnableNonResizableMultiWindow")
                        .setBoolean(param.thisObject, true)
                }
            }
    }
}