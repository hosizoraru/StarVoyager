package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object IconCellCount : HookRegister() {
    override fun init() = hasEnable("home_unlock_cell_count") {
        val clazzSet = setOf(
            loadClass("com.miui.home.launcher.compat.LauncherCellCountCompatDevice"),
            loadClass("com.miui.home.launcher.compat.LauncherCellCountCompatJP"),
            loadClass("com.miui.home.launcher.compat.LauncherCellCountCompatNoWord"),
            loadClass("com.miui.home.launcher.compat.LauncherCellCountCompatResource")
        )
        clazzSet.forEach {
            it.methodFinder().findSuper().filterByName("getCellCountXMin").first().createHook {
                returnConstant(4)
            }
            it.methodFinder().findSuper().filterByName("getCellCountYMin").first().createHook {
                returnConstant(6)
            }
            it.methodFinder().findSuper().filterByName("getCellCountXMax").first().createHook {
                returnConstant(16)
            }
            it.methodFinder().findSuper().filterByName("getCellCountYMax").first().createHook {
                returnConstant(18)
            }
        }
    }
}

//val deviceConfigClass =
//    ClassUtils.loadClassOrNull("com.miui.home.launcher.DeviceConfig")
//val launcherCellCountCompatDeviceClass =
//    ClassUtils.loadClassOrNull("com.miui.home.launcher.compat.LauncherCellCountCompatDevice")
//val launcherCellCountCompatResourceClass =
//    ClassUtils.loadClassOrNull("com.miui.home.launcher.compat.LauncherCellCountCompatResource")
//val utilitiesClass =
//    ClassUtils.loadClassOrNull("com.miui.home.launcher.common.Utilities")
//val screenUtilsClass =
//    ClassUtils.loadClassOrNull("com.miui.home.launcher.ScreenUtils")
//val miuiHomeSettings =
//    ClassUtils.loadClassOrNull("com.miui.home.settings.MiuiHomeSettings")
//
//launcherCellCountCompatDeviceClass?.methodFinder()
//?.filterByName("shouldUseDeviceValue")
//?.toList()?.createHooks {
//    returnConstant(false)
//}
//
//miuiHomeSettings?.methodFinder()
//?.filterByName("onCreatePreferences")
//?.filterByParamCount(2)
//?.filterByParamTypes(Bundle::class.java, String::class.java)
//?.first()?.createHook {
//    after {
//        it.thisObject.getObjectField("mScreenCellsConfig")
//            ?.callMethodOrNull("setVisible", true)
//    }
//}
//
//deviceConfigClass?.hookAfterMethod(
//"loadCellsCountConfig",
//Context::class.java,
//Boolean::class.javaPrimitiveType
//) {
//    val sCellCountY = deviceConfigClass.getStaticObjectField("sCellCountY") as Int
//    if (sCellCountY > 6) {
//        val cellHeight = deviceConfigClass.callStaticMethod("getCellHeight") as Int
//        deviceConfigClass.setStaticObjectField("sFolderCellHeight", cellHeight)
//    }
//}
//
//launcherCellCountCompatResourceClass?.replaceMethod(
//"getCellCountXMax",
//Context::class.java
//) {
//    return@replaceMethod 8
//}
//launcherCellCountCompatResourceClass?.replaceMethod(
//"getCellCountYMax",
//Context::class.java
//) {
//    return@replaceMethod 12
//}
//
//var hook: Set<XC_MethodHook.Unhook>? = null
//screenUtilsClass?.methodFinder()
//?.filterByName("getScreenCellsSizeOptions")
//?.toList()?.createHooks {
//    before {
//        hook = utilitiesClass?.hookBeforeAllMethods("isNoWordModel") {
//            it.result = false
//        }
//    }
//    after {
//        hook?.forEach {
//            it.unhook()
//        }
//    }
//}