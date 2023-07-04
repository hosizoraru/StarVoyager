package star.sky.voyager.hook.hooks.fileexplorer

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister

object AndroidLimit : HookRegister() {
    override fun init() {
        val scopeStorageUtilsCls =
            loadClass("com.android.fileexplorer.util.ScopeStorageUtils")

        scopeStorageUtilsCls.methodFinder()
            .filterByName("isLimit")
            .first().createHook {
                returnConstant(false)
            }
        scopeStorageUtilsCls.methodFinder()
            .filterByName("null")
            .first().createHook {
                returnConstant(null)
            }
    }
}