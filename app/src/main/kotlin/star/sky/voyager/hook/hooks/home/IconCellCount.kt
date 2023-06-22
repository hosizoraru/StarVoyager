package star.sky.voyager.hook.hooks.home

import android.content.Context
import android.os.Bundle
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XC_MethodHook
import star.sky.voyager.utils.api.callMethodOrNull
import star.sky.voyager.utils.api.callStaticMethod
import star.sky.voyager.utils.api.getObjectField
import star.sky.voyager.utils.api.getStaticObjectField
import star.sky.voyager.utils.api.hookAfterMethod
import star.sky.voyager.utils.api.hookBeforeAllMethods
import star.sky.voyager.utils.api.replaceMethod
import star.sky.voyager.utils.api.setStaticObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object IconCellCount : HookRegister() {
    override fun init() = hasEnable("home_unlock_cell_count") {
        val deviceConfigClass =
            loadClassOrNull("com.miui.home.launcher.DeviceConfig")
        val launcherCellCountCompatDeviceClass =
            loadClassOrNull("com.miui.home.launcher.compat.LauncherCellCountCompatDevice")
        val launcherCellCountCompatResourceClass =
            loadClassOrNull("com.miui.home.launcher.compat.LauncherCellCountCompatResource")
        val utilitiesClass =
            loadClassOrNull("com.miui.home.launcher.common.Utilities")
        val screenUtilsClass =
            loadClassOrNull("com.miui.home.launcher.ScreenUtils")
        val miuiHomeSettings =
            loadClassOrNull("com.miui.home.settings.MiuiHomeSettings")

        launcherCellCountCompatDeviceClass?.methodFinder()
            ?.filterByName("shouldUseDeviceValue")
            ?.toList()?.createHooks {
                returnConstant(false)
            }

        miuiHomeSettings?.methodFinder()
            ?.filterByName("onCreatePreferences")
            ?.filterByParamCount(2)
            ?.filterByParamTypes(Bundle::class.java, String::class.java)
            ?.first()?.createHook {
                after {
                    it.thisObject.getObjectField("mScreenCellsConfig")
                        ?.callMethodOrNull("setVisible", true)
                }
            }

        deviceConfigClass?.hookAfterMethod(
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

        launcherCellCountCompatResourceClass?.replaceMethod(
            "getCellCountXMax",
            Context::class.java
        ) {
            return@replaceMethod 8
        }
        launcherCellCountCompatResourceClass?.replaceMethod(
            "getCellCountYMax",
            Context::class.java
        ) {
            return@replaceMethod 12
        }

        var hook: Set<XC_MethodHook.Unhook>? = null
        screenUtilsClass?.methodFinder()
            ?.filterByName("getScreenCellsSizeOptions")
            ?.toList()?.createHooks {
                before {
                    hook = utilitiesClass?.hookBeforeAllMethods("isNoWordModel") {
                        it.result = false
                    }
                }
                after {
                    hook?.forEach {
                        it.unhook()
                    }
                }
            }
    }
}