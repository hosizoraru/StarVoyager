package star.sky.voyager.hook.hooks.packageinstaller

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable

object CustomInstallationSource : HookRegister() {
    override fun init() = hasEnable("custom_installation_source") {
        val yourSource = getString("your_source", "com.android.fileexplorer")
        val qwq = loadClass("com.miui.packageInstaller.InstallStart").methodFinder()
            .filterByName("getCallingPackage").first()
        qwq.createHook {
            returnConstant(yourSource)
//                Log.i("Source: $yourSource")
        }
    }
}