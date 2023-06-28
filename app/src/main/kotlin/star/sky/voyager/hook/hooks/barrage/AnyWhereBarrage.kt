package star.sky.voyager.hook.hooks.barrage

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object AnyWhereBarrage : HookRegister() {
    override fun init() = hasEnable("any_where_barrage") {
        loadClass("android.provider.Settings\$Secure").methodFinder()
            .filterByName("getInt")
            .toList().createHooks {
                before {
                    (it.args[1] == "gb_boosting").apply {
                        it.result = 1
                    }
                }
            }
    }
}