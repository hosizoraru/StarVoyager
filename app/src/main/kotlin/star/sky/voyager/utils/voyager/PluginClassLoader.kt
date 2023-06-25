package star.sky.voyager.utils.voyager

import android.content.pm.ApplicationInfo
import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder

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
    fun hookPluginClassLoader(onGetClassLoader: (appInfo: ApplicationInfo, classLoader: ClassLoader) -> Unit) {
        val classLoaderClass =
            ClassUtils.loadClass("com.android.systemui.shared.plugins.PluginInstance\$Factory")
        classLoaderClass.methodFinder().first {
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
            }
        }
    }
}