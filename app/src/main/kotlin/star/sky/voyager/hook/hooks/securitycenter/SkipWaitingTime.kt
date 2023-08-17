package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object SkipWaitingTime : HookRegister() {
    override fun init() = hasEnable("skip_waiting_time") {
        val mInterceptBaseFragmentCls =
            loadClass("com.miui.permcenter.privacymanager.InterceptBaseFragment")
//        val mInnerClasses = mInterceptBaseFragmentCls.declaredClasses

        loadClass("android.widget.TextView").methodFinder()
            .filterByName("setText")
            .filterByParamCount(4)
            .first().createHook {
                before {
                    if (it.args.isNotEmpty() && it.args[0]?.toString()?.startsWith("确定(") == true
                    ) {
                        it.args[0] = "确定"
                    }
                }
            }

        loadClass("android.widget.TextView").methodFinder()
            .filterByName("setEnabled")
            .filterByParamTypes(Boolean::class.java)
            .first().createHook {
                before {
                    it.args[0] = true
                }
            }

        // 节省性能，以下hook非必需
//        mInnerClasses.firstOrNull { Handler::class.java.isAssignableFrom(it) }?.let { clazz ->
//            clazz.declaredConstructors.filter { it.parameterCount == 2 }.createHooks {
//                before {
//                    it.args[1] = 0
//                }
//            }
//            clazz.methodFinder().filterByAssignableReturnType(Void.TYPE).filterByAssignableParamTypes(Int::class.javaPrimitiveType)
//                .first().createHook {
//                    before {
//                        it.args[0] = 0
//                    }
//                }
//        }
    }
}