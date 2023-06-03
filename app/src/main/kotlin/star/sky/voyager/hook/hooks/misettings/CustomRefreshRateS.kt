package star.sky.voyager.hook.hooks.misettings

import android.os.Bundle
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils.setObject
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object CustomRefreshRateS : HookRegister() {
    override fun init() = hasEnable("custom_refresh_rate") {
        loadClass("com.xiaomi.misettings.display.RefreshRate.NewRefreshRateFragment").methodFinder()
            .filterByName("b")
            .filterByParamCount(1)
            .filterByParamTypes(Boolean::class.java)
            .first().createHook {
                before {
                    it.args[0] = true
                }
            }

        loadClass("com.xiaomi.misettings.display.RefreshRate.RefreshRateActivity").methodFinder()
            .filterByName("onCreate")
            .filterByParamCount(1)
            .filterByParamTypes(Bundle::class.java)
            .first().createHook {
                before {
                    setObject(it.thisObject, "a", true)
                }
            }
    }
}