package star.sky.voyager.utils.voyager

import android.content.pm.ApplicationInfo
import android.os.Build
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XC_MethodHook

object PluginClassLoader {
    /**
     * 可以从 SystemUI 里获取 SystemUIPlugin 的 AppInfo 和 ClassLoader
     *
     * 使用方法:
     *
     *     hookPluginClassLoader { appInfo, classLoader ->
     *         // 在这里使用 appInfo 和 classLoader 变量
     *     }
     *
     * 仅适用于 Mix4 A13 Miui14 V14.0.23.5.22.Dev 开发版
     * @author Voyager
     * @return appInfo & classLoaderP
     */
    var hook: XC_MethodHook.Unhook? = null
    private var pluginAppInfo: ApplicationInfo? = null
    private var pluginClsLoader: ClassLoader? = null
    fun hookPluginClassLoader(onGetClassLoader: (appInfo: ApplicationInfo, classLoader: ClassLoader) -> Unit) {
        when (Build.VERSION.SDK_INT) {
            Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {
                loadClass("com.android.systemui.shared.plugins.PluginInstance\$Factory")
                    .methodFinder().filterByName("create").toList().createHooks {
                        before {
                            pluginAppInfo = it.args[1] as ApplicationInfo
                        }
                    }
                hook =
                    loadClass("com.android.systemui.shared.plugins.PluginInstance\$Factory\$\$ExternalSyntheticLambda0")
                        .methodFinder().filterByName("get").first().createHook {
                            var isHooked = false
                            after {
                                val patchClassLoader =
                                    it.result
                                if (pluginAppInfo != null) {
                                    if (pluginAppInfo?.packageName.equals("miui.systemui.plugin") && !isHooked) {
                                        isHooked = true
                                        if (pluginClsLoader == null) {
                                            pluginClsLoader =
                                                patchClassLoader as ClassLoader
//                                            Log.i("get classLoader: $pluginAppInfo $pluginClsLoader")
                                            onGetClassLoader(pluginAppInfo!!, pluginClsLoader!!)
                                        }
                                    }
                                }
                                hook?.unhook()
                            }
                        }
            }

            Build.VERSION_CODES.TIRAMISU -> {
                val classLoaderClass =
                    loadClass("com.android.systemui.shared.plugins.PluginInstance\$Factory")
                hook = classLoaderClass.methodFinder().first {
                    name == "getClassLoader"
                            && parameterCount == 2
                            && parameterTypes[0] == ApplicationInfo::class.java
                            && parameterTypes[1] == ClassLoader::class.java
                }.createHook {
                    after { getClassLoader ->
                        val appInfo = getClassLoader.args[0] as ApplicationInfo
                        val classLoaderP = getClassLoader.result as ClassLoader
//            Log.i("get classLoader: $appInfo $classLoaderP")
                        if (appInfo.packageName == "miui.systemui.plugin") {
                            onGetClassLoader(appInfo, classLoaderP)
                        }
                        hook?.unhook()
                    }
                }
            }
        }
    }
}