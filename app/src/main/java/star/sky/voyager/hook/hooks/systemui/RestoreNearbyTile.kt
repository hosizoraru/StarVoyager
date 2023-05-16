package star.sky.voyager.hook.hooks.systemui

import android.content.pm.ApplicationInfo
import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.LogExtensions.logexIfThrow
import com.github.kyuubiran.ezxhelper.MemberExtensions.paramCount
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XposedHelpers
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object RestoreNearbyTile : HookRegister() {
    var isTrulyInit: Boolean = false
    override fun init() = hasEnable("restore_near_by_tile") {
        ClassUtils.loadClass("com.android.systemui.shared.plugins.PluginInstance\$Factory").methodFinder().first {
            name == "getClassLoader" && paramCount == 2 && parameterTypes[0] == ApplicationInfo::class.java && parameterTypes[1] == ClassLoader::class.java
        }.createHook {
            after { param ->
                if (!isTrulyInit) kotlin.runCatching {
                    val applicationInfo = param.args[0] as ApplicationInfo
                    val pluginClassLoader = param.result as? ClassLoader ?: return@after
                    if (applicationInfo.packageName != "miui.systemui.plugin") return@after
                    ClassUtils.loadClass(
                        "miui.systemui.controlcenter.qs.customize.TileQueryHelper\$Companion",
                        pluginClassLoader
                    ).methodFinder().filterByName("filterNearby").first().createHook {
                        returnConstant(false)
                    }
                    isTrulyInit = true
                    Log.ix("Truly inited hook: ${this@RestoreNearbyTile.javaClass.simpleName}")
                }.logexIfThrow("Failed truly init hook: ${this@RestoreNearbyTile.javaClass.simpleName}")
            }
        }
        if (!ClassUtils.loadClass("miui.os.Build").getField("IS_INTERNATIONAL_BUILD").getBoolean(null)) {
            val isInternationalHook: HookFactory.() -> Unit = {
                val constantsClazz =
                    ClassUtils.loadClass("com.android.systemui.controlcenter.utils.Constants")
                before {
                    XposedHelpers.setStaticBooleanField(constantsClazz, "IS_INTERNATIONAL", true)
                }
                after {
                    XposedHelpers.setStaticBooleanField(constantsClazz, "IS_INTERNATIONAL", false)
                }
            }

            ClassUtils.loadClass("com.android.systemui.qs.MiuiQSTileHostInjector").methodFinder().first {
                name == "createMiuiTile"
            }.createHook(isInternationalHook)

            ClassUtils.loadClass("com.android.systemui.controlcenter.utils.ControlCenterUtils").methodFinder().first {
                name == "filterCustomTile"
            }.createHook(isInternationalHook)
        }
    }
}