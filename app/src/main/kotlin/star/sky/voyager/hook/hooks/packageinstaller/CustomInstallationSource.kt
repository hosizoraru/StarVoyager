package star.sky.voyager.hook.hooks.packageinstaller

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable

object CustomInstallationSource : HookRegister() {
    private val yourSource by lazy {
        getString("your_source", "com.android.fileexplorer")
    }

    override fun init() = hasEnable("custom_installation_source") {
        loadClass("com.miui.packageInstaller.InstallStart").methodFinder()
            .filterByName("getCallingPackage")
            .first().createHook {
                returnConstant(yourSource)
//                Log.i("Source: $yourSource")
            }
    }
}