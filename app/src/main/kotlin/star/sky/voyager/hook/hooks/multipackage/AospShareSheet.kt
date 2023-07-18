package star.sky.voyager.hook.hooks.multipackage

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object AospShareSheet : HookRegister() {
    override fun init() = hasEnable("aosp_share_sheet") {
        setOf(
            loadClass("com.android.internal.app.ResolverActivityStubImpl"),
            loadClass("com.android.internal.app.ResolverActivityStub")
        ).forEach { clazz ->
            clazz.methodFinder()
                .filterByName("useAospShareSheet")
                .first().createHook {
                    returnConstant(true)
                }
        }
    }
}