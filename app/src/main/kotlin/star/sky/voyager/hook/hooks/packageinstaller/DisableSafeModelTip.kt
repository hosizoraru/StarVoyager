package star.sky.voyager.hook.hooks.packageinstaller

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.FieldFinder.`-Static`.fieldFinder
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object DisableSafeModelTip : HookRegister() {
    override fun init() = hasEnable("Disable_Safe_Model_Tip") {
        try {
            loadClassOrNull("com.miui.packageInstaller.model.ApkInfo")!!.methodFinder()
                .filterByName("getSystemApp")
                .first().createHook {
                    returnConstant(true)
                }
            val installProgressActivityClass =
                loadClassOrNull("com.miui.packageInstaller.InstallProgressActivity")!!
            installProgressActivityClass.methodFinder()
                .filterByName("g0")
                .first().createHook {
                    returnConstant(false)
                }
            installProgressActivityClass.methodFinder()
                .filterByName("Q1")
                .first().createHook {
                    before {
                        it.result = null
                    }
                }
            installProgressActivityClass.methodFinder()
                .filter {
                    true
                }.toList().createHooks {
                    after { param ->
                        param.thisObject.javaClass.fieldFinder().first {
                            type == Boolean::class.java
                        }.setBoolean(param.thisObject, false)
                    }
                }
        } catch (_: Throwable) {

        }
    }
}