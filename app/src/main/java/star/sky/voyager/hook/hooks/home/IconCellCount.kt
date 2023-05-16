package star.sky.voyager.hook.hooks.home

import android.content.Context
import com.github.kyuubiran.ezxhelper.EzXHelper
import de.robv.android.xposed.XC_MethodHook
import star.sky.voyager.utils.api.callMethod
import star.sky.voyager.utils.api.callStaticMethod
import star.sky.voyager.utils.api.findClass
import star.sky.voyager.utils.api.getObjectField
import star.sky.voyager.utils.api.getStaticObjectField
import star.sky.voyager.utils.api.hookAfterAllMethods
import star.sky.voyager.utils.api.hookAfterMethod
import star.sky.voyager.utils.api.hookBeforeAllMethods
import star.sky.voyager.utils.api.replaceMethod
import star.sky.voyager.utils.api.setStaticObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object IconCellCount : HookRegister() {
    override fun init() = hasEnable("home_unlock_cell_count") {
        val deviceConfigClass =
            "com.miui.home.launcher.DeviceConfig".findClass(EzXHelper.classLoader)
        val launcherCellCountCompatDeviceClass =
            "com.miui.home.launcher.compat.LauncherCellCountCompatDevice".findClass(EzXHelper.classLoader)
        val launcherCellCountCompatResourceClass =
            "com.miui.home.launcher.compat.LauncherCellCountCompatResource".findClass(EzXHelper.classLoader)
        val utilitiesClass =
            "com.miui.home.launcher.common.Utilities".findClass(EzXHelper.classLoader)
        val screenUtilsClass = "com.miui.home.launcher.ScreenUtils".findClass(EzXHelper.classLoader)
        val miuiHomeSettings =
            "com.miui.home.settings.MiuiHomeSettings".findClass(EzXHelper.classLoader)

        launcherCellCountCompatDeviceClass.replaceMethod("shouldUseDeviceValue") {
            return@replaceMethod false
        }

        miuiHomeSettings.hookAfterMethod("onCreatePreferences") {
            it.thisObject.getObjectField("mScreenCellsConfig")?.callMethod("setVisible", true)
        }

        deviceConfigClass.hookAfterMethod(
            "loadCellsCountConfig",
            Context::class.java,
            Boolean::class.javaPrimitiveType
        ) {
            val sCellCountY = deviceConfigClass.getStaticObjectField("sCellCountY") as Int
            if (sCellCountY > 6) {
                val cellHeight = deviceConfigClass.callStaticMethod("getCellHeight") as Int
                deviceConfigClass.setStaticObjectField("sFolderCellHeight", cellHeight)
            }
        }

        launcherCellCountCompatResourceClass.replaceMethod(
            "getCellCountXMax",
            Context::class.java
        ) {
            return@replaceMethod 8
        }
        launcherCellCountCompatResourceClass.replaceMethod(
            "getCellCountYMax",
            Context::class.java
        ) {
            return@replaceMethod 12
        }

        var hook: Set<XC_MethodHook.Unhook>? = null
        screenUtilsClass.hookBeforeAllMethods("getScreenCellsSizeOptions") {
            hook = utilitiesClass.hookBeforeAllMethods("isNoWordModel") {
                it.result = false
            }
        }
        screenUtilsClass.hookAfterAllMethods("getScreenCellsSizeOptions") {
            hook?.forEach {
                it.unhook()
            }
        }
    }
}