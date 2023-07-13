package star.sky.voyager.hook.hooks.personalassistant

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import java.util.Locale

object LocationCN : HookRegister() {
    override fun init() = hasEnable("assistant_location_cn") {
        loadClass("java.util.Locale")
            .getDeclaredMethod("getDefault")
            .createHook {
                after {
//                    val locale = param.result as Locale
//                    val builder = Locale.Builder().setLanguage("zh").setRegion("CN")
//                    builder.setLanguage("zh")
//                    builder.setRegion("CN")
//                    Locale.setDefault(builder.build())
                    val locale = it.result as Locale
                    Locale.setDefault(Locale("zh", "CN"))
                }
            }
    }
}