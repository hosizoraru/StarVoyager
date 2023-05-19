package star.sky.voyager.hook.hooks.packageinstaller

import com.github.kyuubiran.ezxhelper.EzXHelper
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import star.sky.voyager.utils.yife.DexKit.loadDexKit


object AllowUpdateSystemApp : HookRegister() {
    override fun init() = hasEnable("package_installer_allow_update_system_app") {
        loadDexKit()
        dexKitBridge.findMethod {
            methodParamTypes = arrayOf("Landroid/content/pm/ApplicationInfo;")
            methodReturnType = "boolean"
        }.forEach {
            it.getMethodInstance(EzXHelper.classLoader).createHook {
                returnConstant(true)
            }
        }
    }
}