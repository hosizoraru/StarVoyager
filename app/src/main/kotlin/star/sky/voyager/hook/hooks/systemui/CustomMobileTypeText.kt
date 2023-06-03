package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils
import star.sky.voyager.utils.key.hasEnable

object CustomMobileTypeText : HookRegister() {
    override fun init() = hasEnable("custom_mobile_type_text_switch") {
        loadClass("com.android.systemui.statusbar.connectivity.MobileSignalController").methodFinder()
            .filterByName("getMobileTypeName")
            .filterByParamTypes(Int::class.java)
            .first().createHook {
                after {
                    it.result = XSPUtils.getString("custom_mobile_type_text", "5G")
                }
            }
    }
}