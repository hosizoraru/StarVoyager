package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.findClass
import star.sky.voyager.utils.api.hookBeforeMethod
import star.sky.voyager.utils.api.replaceMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object SetDeviceLevel : HookRegister() {
    override fun init() = hasEnable("home_high_end_device") {
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
            "com.miui.home.launcher.common.DeviceLevelUtils".hookBeforeMethod(
                EzXHelper.classLoader,
                "getDeviceLevel"
            ) {
                it.result = 2
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            "com.miui.home.launcher.DeviceConfig".hookBeforeMethod(
                EzXHelper.classLoader,
                "isSupportCompleteAnimation"
            ) {
                it.result = true
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            "com.miui.home.launcher.common.DeviceLevelUtils".hookBeforeMethod(
                EzXHelper.classLoader,
                "isLowLevelOrLiteDevice"
            ) {
                it.result = false
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            "com.miui.home.launcher.DeviceConfig".hookBeforeMethod(
                EzXHelper.classLoader,
                "isMiuiLiteVersion"
            ) {
                it.result = false
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            "com.miui.home.launcher.util.noword.NoWordSettingHelperKt".hookBeforeMethod(
                EzXHelper.classLoader,
                "isNoWordAvailable"
            ) {
                it.result = true
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }

        try {
            "android.os.SystemProperties".hookBeforeMethod(
                EzXHelper.classLoader, "getBoolean", String::class.java, Boolean::class.java
            ) {
                if (it.args[0] == "ro.config.low_ram.threshold_gb") it.result = false
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            "android.os.SystemProperties".hookBeforeMethod(
                EzXHelper.classLoader, "getBoolean", String::class.java, Boolean::class.java
            ) {
                if (it.args[0] == "ro.miui.backdrop_sampling_enabled") it.result = true
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            "com.miui.home.launcher.common.Utilities".hookBeforeMethod(
                EzXHelper.classLoader,
                "canLockTaskView"
            ) {
                it.result = true
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            "com.miui.home.launcher.MIUIWidgetUtil".hookBeforeMethod(
                EzXHelper.classLoader,
                "isMIUIWidgetSupport"
            ) {
                it.result = true
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            "com.miui.home.launcher.MiuiHomeLog".findClass(EzXHelper.classLoader).replaceMethod(
                "log", String::class.java, String::class.java
            ) {
                return@replaceMethod null
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
        try {
            "com.xiaomi.onetrack.OneTrack".hookBeforeMethod(EzXHelper.classLoader, "isDisable") {
                it.result = true
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
    }
}