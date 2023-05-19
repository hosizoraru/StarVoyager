package star.sky.voyager.hook.hooks.packageinstaller

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils
import star.sky.voyager.utils.key.hasEnable

object CustomInstallationSource : HookRegister() {
    override fun init() {
        val qwq = loadClass("com.miui.packageInstaller.InstallStart").methodFinder().first {
            name == "getCallingPackage"
        }
        val yourSource = XSPUtils.getString("your_source", "com.android.fileexplorer")
        hasEnable("custom_installation_source") {
            qwq.createHook {
                returnConstant(yourSource)
                Log.i("Source: $yourSource")
            }
        }
    }
}