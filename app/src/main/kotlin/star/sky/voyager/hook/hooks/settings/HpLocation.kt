package star.sky.voyager.hook.hooks.settings

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object HpLocation : HookRegister() {
    override fun init() = hasEnable("hp_location") {
        loadClass("com.android.settings.location.XiaomiHpLocationController")
            .methodFinder().filter {
                name in setOf("hasXiaomiHpFeature", "isZh")
            }.toList().createHooks {
                returnConstant(true)
            }
    }
}