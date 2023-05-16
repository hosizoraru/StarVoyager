package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.setObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object WaveCharge : HookRegister() {
    override fun init() = hasEnable("enable_wave_charge_animation") {
        loadClass("com.android.keyguard.charge.ChargeUtils").methodFinder().first {
            name == "supportWaveChargeAnimation"
        }.createHook {
            after {
                val ex = Throwable()
                val stackElement = ex.stackTrace
                var mResult = false
                val classTrue = setOf(
                    "com.android.keyguard.charge.ChargeUtils",
                    "com.android.keyguard.charge.container.MiuiChargeContainerView"
                )
                for (i in stackElement.indices) {
                    when (stackElement[i].className) {
                        in classTrue -> {
                            mResult = true
                            break
                        }
                    }
                }
                it.result = mResult
            }
        }
        loadClass("com.android.keyguard.charge.wave.WaveView").methodFinder().first {
            name == "updateWaveHeight"
        }.createHook {
            after {
                it.thisObject.setObjectField("mWaveXOffset", 0)
            }
        }
    }
}