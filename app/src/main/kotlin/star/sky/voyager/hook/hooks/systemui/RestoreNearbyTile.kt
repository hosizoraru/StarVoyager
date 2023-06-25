package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.HookFactory
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.LogExtensions.logexIfThrow
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.PluginClassLoader.hookPluginClassLoader
import star.sky.voyager.utils.yife.Build.IS_INTERNATIONAL_BUILD

object RestoreNearbyTile : HookRegister() {
    private var isTrulyInit: Boolean = false
    override fun init() = hasEnable("restore_near_by_tile") {
//        loadClass("com.android.systemui.shared.plugins.PluginInstance\$Factory").methodFinder()
//            .filterByName("getClassLoader")
//            .filterByParamCount(2)
//            .filterByParamTypes(ApplicationInfo::class.java, ClassLoader::class.java)
//            .first().createHook {
//                after { param ->
//                    if (!isTrulyInit) kotlin.runCatching {
//                        val applicationInfo = param.args[0] as ApplicationInfo
//                        val pluginClassLoader = param.result as? ClassLoader ?: return@after
//                        if (applicationInfo.packageName != "miui.systemui.plugin") return@after
//                        loadClass(
//                            "miui.systemui.controlcenter.qs.customize.TileQueryHelper\$Companion",
//                            pluginClassLoader
//                        ).methodFinder().filterByName("filterNearby").first().createHook {
//                            returnConstant(false)
//                        }
//                        isTrulyInit = true
////                        Log.i("Truly inited hook: ${this@RestoreNearbyTile.javaClass.simpleName}")
//                    }
//                        .logexIfThrow("Failed truly init hook: ${this@RestoreNearbyTile.javaClass.simpleName}")
//                }
//            }
        hookPluginClassLoader { _, classLoader ->
            if (!isTrulyInit) kotlin.runCatching {
                loadClass(
                    "miui.systemui.controlcenter.qs.customize.TileQueryHelper\$Companion",
                    classLoader
                ).methodFinder().filterByName("filterNearby").first().createHook {
                    returnConstant(false)
                }
                isTrulyInit = true
            }.logexIfThrow("Failed truly init hook: ${this@RestoreNearbyTile.javaClass.simpleName}")
        }

        if (!IS_INTERNATIONAL_BUILD) {
            if (IS_INTERNATIONAL_BUILD) return@hasEnable
            val isInternationalHook: HookFactory.() -> Unit = {
                val constantsClazz = loadClass("com.android.systemui.controlcenter.utils.Constants")
                before {
                    setStaticObject(constantsClazz, "IS_INTERNATIONAL", true)
                }
                after {
                    setStaticObject(constantsClazz, "IS_INTERNATIONAL", false)
                }
            }

            loadClass("com.android.systemui.qs.MiuiQSTileHostInjector").methodFinder().first {
                name == "createMiuiTile"
            }.createHook(isInternationalHook)

            loadClass("com.android.systemui.controlcenter.utils.ControlCenterUtils").methodFinder()
                .first {
                    name == "filterCustomTile"
                }.createHook(isInternationalHook)
        }
    }
}