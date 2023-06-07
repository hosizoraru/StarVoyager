package star.sky.voyager.hook.hooks.cast

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object ForceSupportSendApp : HookRegister() {
    override fun init() = hasEnable("force_support_send_app") {
        when (EzXHelper.hostPackageName) {
            "com.milink.service" -> {
                loadClass("com.xiaomi.mirror.synergy.MiuiSynergySdk").methodFinder()
                    .filterByName("isSupportSendApp")
                    .first().createHook {
                        after {
                            it.result = true
                        }
                    }
            }

            "com.xiaomi.mirror" -> {
                val clazzRelayAppMessage = loadClass("com.xiaomi.mirror.message.RelayAppMessage")
                val clazzMiCloudUtils = loadClass("com.xiaomi.mirror.settings.micloud.MiCloudUtils")
                clazzRelayAppMessage.methodFinder()
                    .filterByAssignableReturnType(clazzRelayAppMessage)
                    .toList().createHooks {
                        after {
                            it.result.objectHelper().setObject("isHideIcon", false)
                        }
                    }
                clazzMiCloudUtils.methodFinder()
                    .filterByName("isSupportSubScreen")
                    .first().createHook {
                        returnConstant(true)
                    }
            }
        }
    }
}