package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.getObjectField
import star.sky.voyager.utils.api.setObjectField
import star.sky.voyager.utils.init.HookRegister

object IconBreakAnimation : HookRegister() {
    override fun init() {
        loadClass("com.miui.home.recents.NavStubView").methodFinder()
            .filterByName("onInputConsumerEvent")
            .first().createHook {
                before { param ->
                    val mAppToHomeAnim2Bak = param.thisObject.getObjectField("mAppToHomeAnim2")
                    if (mAppToHomeAnim2Bak != null) {
                        param.thisObject.setObjectField("mAppToHomeAnim2", null as Any?)
                    }
                }

                after { param ->
                    if (param.thisObject.getObjectField("mAppToHomeAnim2") != null) {
                        return@after
                    }
                    param.thisObject.setObjectField(
                        "mAppToHomeAnim2",
                        param.thisObject.getObjectField("mAppToHomeAnim2Bak")
                    )
                }
            }

        loadClass("com.miui.home.launcher.ItemIcon").methodFinder()
            .filterByName("initPerformClickRunnable")
            .first().createHook {
                before { param ->
                    param.result = Runnable {}
                }
            }
    }
}