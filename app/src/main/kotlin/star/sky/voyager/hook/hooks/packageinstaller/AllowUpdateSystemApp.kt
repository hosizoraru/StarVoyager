package star.sky.voyager.hook.hooks.packageinstaller

import android.content.pm.ApplicationInfo
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.EzXHelper
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.Log
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
        var foundMethod = false // 标志变量
        for (i in 0..25) {
            val classIfExists = loadClassOrNull("j2.${letter}")
            classIfExists?.let {
                if (it.declaredMethods.size in 15..25) {
                    val qwq = classIfExists.declaredMethods.toList()
                    for (qaq in qwq) {
                        if (qaq.parameterTypes[0] == ApplicationInfo::class.java) {
                            qaq.createHook {
                                before { param ->
                                    param.result = false
                                }
                            }
                            foundMethod = true // 找到匹配的方法并创建了钩子
                            break
                        }
                    }
                }
            }
            if (foundMethod) {
                break // 跳出外部循环
            }
            letter++
        }
    }
}