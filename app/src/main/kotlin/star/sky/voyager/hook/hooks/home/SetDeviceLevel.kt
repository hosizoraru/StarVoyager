package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.findClass
import star.sky.voyager.utils.api.hookBeforeMethod
import star.sky.voyager.utils.api.replaceMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object SetDeviceLevel : HookRegister() {
    override fun init() = hasEnable("home_high_end_device") {
        val deviceLevelUtilsClass = loadClass("com.miui.home.launcher.common.DeviceLevelUtils")
        val systemPropertiesClass = loadClass("android.os.SystemProperties")
        val deviceConfigClass = loadClass("com.miui.home.launcher.DeviceConfig")
        try {
            loadClass("com.miui.home.launcher.common.CpuLevelUtils").methodFinder()
                .filterByName("getQualcommCpuLevel")
                .filterByParamCount(1)
                .first()
        } catch (e: Exception) {
            loadClass("miuix.animation.utils.DeviceUtils").methodFinder()
                .filterByName("getQualcommCpuLevel")
                .filterByParamCount(1)
                .first()
        }.createHook {
            returnConstant(2)
        }
        try {
            deviceConfigClass.methodFinder()
                .filterByName("isUseSimpleAnim")
                .first().createHook {
                    before {
                        it.result = false
                    }
                }
        } catch (_: Throwable) {

        }
        try {
            deviceLevelUtilsClass.methodFinder()
                .filterByName("getDeviceLevel")
                .first().createHook {
                    before {
                        it.result = 2
                    }
                }
        } catch (_: Throwable) {

        }
        try {
            deviceConfigClass.methodFinder()
                .filterByName("isSupportCompleteAnimation")
                .first().createHook {
                    before {
                        it.result = true
                    }
                }
        } catch (_: Throwable) {

        }
        try {
            deviceLevelUtilsClass.methodFinder()
                .filterByName("isLowLevelOrLiteDevice")
                .first().createHook {
                    before {
                        it.result = false
                    }
                }
        } catch (_: Throwable) {

        }
        try {
            deviceConfigClass.methodFinder()
                .filterByName("isMiuiLiteVersion")
                .first().createHook {
                    before {
                        it.result = false
                    }
                }
        } catch (_: Throwable) {

        }
        try {
            "com.miui.home.launcher.util.noword.NoWordSettingHelperKt".hookBeforeMethod(
                classLoader,
                "isNoWordAvailable"
            ) {
                it.result = true
            }
        } catch (_: Throwable) {

        }

        try {
            systemPropertiesClass.methodFinder()
                .filterByName("getBoolean")
                .filterByParamTypes(String::class.java, Boolean::class.java)
                .toList().createHooks {
                    before {
                        if (it.args[0] == "ro.config.low_ram.threshold_gb") it.result = false
                        if (it.args[0] == "ro.miui.backdrop_sampling_enabled") it.result = true
                    }
                }
        } catch (_: Throwable) {

        }
        try {
            "com.miui.home.launcher.common.Utilities".hookBeforeMethod(
                classLoader,
                "canLockTaskView"
            ) {
                it.result = true
            }
        } catch (_: Throwable) {

        }
        try {
            "com.miui.home.launcher.MIUIWidgetUtil".hookBeforeMethod(
                classLoader,
                "isMIUIWidgetSupport"
            ) {
                it.result = true
            }
        } catch (_: Throwable) {

        }
        try {
            "com.miui.home.launcher.MiuiHomeLog".findClass(classLoader).replaceMethod(
                "log", String::class.java, String::class.java
            ) {
                return@replaceMethod null
            }
        } catch (_: Throwable) {

        }
        try {
            "com.xiaomi.onetrack.OneTrack".hookBeforeMethod(classLoader, "isDisable") {
                it.result = true
            }
        } catch (_: Throwable) {

        }
    }
}