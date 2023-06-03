package star.sky.voyager.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object Disable72hVerify : HookRegister() {
    override fun init() = hasEnable("disable_72h_verify") {
        loadClass("com.android.server.locksettings.LockSettingsStrongAuth").methodFinder()
            .filterByName("rescheduleStrongAuthTimeoutAlarm")
            .filterByParamTypes(Long::class.java, Int::class.java)
            .first().createHook {
                returnConstant(null)
            }
    }
}