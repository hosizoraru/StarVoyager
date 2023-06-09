package star.sky.voyager.hook.hooks.packageinstaller

import android.content.pm.ApplicationInfo
import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.dexKitBridge
import star.sky.voyager.utils.yife.DexKit.loadDexKit


object AllAsSystemApp : HookRegister() {
    override fun init() = hasEnable("all_as_system_app") {
        loadDexKit()
        dexKitBridge.findMethod {
            methodParamTypes = arrayOf("Landroid/content/pm/ApplicationInfo;")
            methodReturnType = "boolean"
        }.forEach {
            it.getMethodInstance(safeClassLoader).createHook {
                before { param ->
                    (param.args[0] as ApplicationInfo).flags =
                        (param.args[0] as ApplicationInfo).flags.or(ApplicationInfo.FLAG_SYSTEM)
//                    Log.i("看看行不行，被叫方法：" + param.method.declaringClass + "." + param.method.name)
                }
//                returnConstant(true)
            }
        }
    }
}