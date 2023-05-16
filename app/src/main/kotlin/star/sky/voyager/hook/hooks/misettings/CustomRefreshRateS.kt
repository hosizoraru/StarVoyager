package star.sky.voyager.hook.hooks.misettings

import android.os.Bundle
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.MemberExtensions.paramCount
import com.github.kyuubiran.ezxhelper.ObjectUtils.setObject
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object CustomRefreshRateS : HookRegister() {
    override fun init() = hasEnable("custom_refresh_rate") {
        loadClass("com.xiaomi.misettings.display.RefreshRate.NewRefreshRateFragment").methodFinder().first {
            name == "b" && paramCount == 1 && parameterTypes[0] == Boolean::class.java
        }.createHook {
            before {
                it.args[0] = true
            }
        }

        loadClass("com.xiaomi.misettings.display.RefreshRate.RefreshRateActivity").methodFinder().first {
            name == "onCreate" && paramCount == 1 && parameterTypes[0] == Bundle::class.java
        }.createHook {
            before {
                setObject(it.thisObject,"a",true)
            }
        }
    }
}