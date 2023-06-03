package star.sky.voyager.hook.hooks.cast

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object ForceSupportSendApp : HookRegister() {
    override fun init() = hasEnable("force_support_send_app") {
        loadClass("com.xiaomi.mirror.synergy.MiuiSynergySdk").methodFinder()
            .filterByName("isSupportSendApp")
            .first().createHook {
                after {
                    it.result = true
                }
            }
    }
}