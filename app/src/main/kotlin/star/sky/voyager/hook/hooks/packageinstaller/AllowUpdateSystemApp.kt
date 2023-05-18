package star.sky.voyager.hook.hooks.packageinstaller

import android.content.pm.ApplicationInfo
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.EzXHelper
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.hookBeforeMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable


object AllowUpdateSystemApp : HookRegister() {
    override fun init() = hasEnable("package_installer_allow_update_system_app") {
        try {
            "android.os.SystemProperties".hookBeforeMethod(
                EzXHelper.classLoader,
                "getBoolean", String::class.java, Boolean::class.java
            ) {
                if (it.args[0] == "persist.sys.allow_sys_app_update") it.result = true
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }

        var letter = 'a'
        for (i in 0..25) {
            val classIfExists = loadClassOrNull("j2.${letter}")
            classIfExists?.let {
                if (it.declaredMethods.size in 15..25) {
                    it.methodFinder().first {
                        parameterTypes[0] == ApplicationInfo::class.java
                    }.createHook {
                        before { hookParam ->
                            hookParam.result = false
                        }
                    }
                }
            }
            Log.i(letter.toString() + classIfExists.toString())
            letter++
        }
    }
}