package star.sky.voyager.hook.hooks.externalstorage

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object NoStorageRestrict : HookRegister() {
    override fun init() = hasEnable("No_Storage_Restrict") {
        loadClass("com.android.externalstorage.ExternalStorageProvider").methodFinder()
            .filterByName("shouldBlockFromTree")
            .filterByParamTypes(String::class.java)
            .first().createHook {
                returnConstant(false)
            }
    }
}