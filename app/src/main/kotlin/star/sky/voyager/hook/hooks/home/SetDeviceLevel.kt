package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.findClass
import star.sky.voyager.utils.api.hookBeforeMethod
import star.sky.voyager.utils.api.replaceMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object SetDeviceLevel : HookRegister() {
    override fun init() = hasEnable("home_high_end_device") {
        val DeviceLevelUtilsClass = loadClass("com.miui.home.launcher.common.DeviceLevelUtils")
        val SystemPropertiesClass = loadClass("android.os.SystemProperties")
        val DeviceConfigClass = loadClass("com.miui.home.launcher.DeviceConfig")
        try {
            loadClass("com.miui.home.launcher.common.CpuLevelUtils").methodFinder().first {
                name == "getQualcommCpuLevel" && parameterCount == 1
            }
        } catch (e: Exception) {
            loadClass("miuix.animation.utils.DeviceUtils").methodFinder().first {
                name == "getQualcommCpuLevel" && parameterCount == 1
            }
        }.createHook {
            returnConstant(2)
        }
        try {
            DeviceLevelUtilsClass.methodFinder().first {
                name == "getDeviceLevel"
            }.createHook {
                before {
                    it.result = 2
                }
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            DeviceConfigClass.methodFinder().first {
                name == "isSupportCompleteAnimation"
            }.createHook {
                before {
                    it.result = true
                }
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            DeviceLevelUtilsClass.methodFinder().first {
                name == "isLowLevelOrLiteDevice"
            }.createHook {
                before {
                    it.result = false
                }
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            DeviceConfigClass.methodFinder().first {
                name == "isMiuiLiteVersion"
            }.createHook {
                before {
                    it.result = false
                }
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            "com.miui.home.launcher.util.noword.NoWordSettingHelperKt".hookBeforeMethod(
                classLoader,
                "isNoWordAvailable"
            ) {
                it.result = true
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }

        try {
            SystemPropertiesClass.methodFinder().filter {
                name == "getBoolean" && parameterTypes[0] == String::class.java && parameterTypes[1] == Boolean::class.java
            }.toList().createHooks {
                before {
                    if (it.args[0] == "ro.config.low_ram.threshold_gb") it.result = false
                    if (it.args[0] == "ro.miui.backdrop_sampling_enabled") it.result = true
                }
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            "com.miui.home.launcher.common.Utilities".hookBeforeMethod(
                classLoader,
                "canLockTaskView"
            ) {
                it.result = true
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            "com.miui.home.launcher.MIUIWidgetUtil".hookBeforeMethod(
                classLoader,
                "isMIUIWidgetSupport"
            ) {
                it.result = true
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            "com.miui.home.launcher.MiuiHomeLog".findClass(classLoader).replaceMethod(
                "log", String::class.java, String::class.java
            ) {
                return@replaceMethod null
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            "com.xiaomi.onetrack.OneTrack".hookBeforeMethod(classLoader, "isDisable") {
                it.result = true
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
    }
}