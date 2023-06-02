package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object WaveCharge : HookRegister() {
    override fun init() = hasEnable("enable_wave_charge_animation") {
        loadClass("com.android.keyguard.charge.ChargeUtils").methodFinder()
            .filterByName("supportWaveChargeAnimation")
            .first().createHook {
                after { param ->
                    val clazzTrue = setOf(
                        "com.android.keyguard.charge.ChargeUtils",
                        "com.android.keyguard.charge.container.MiuiChargeContainerView"
                    )
                    param.result = Throwable().stackTrace.any { it.className in clazzTrue }
                }
            }
        loadClass("com.android.keyguard.charge.wave.WaveView").methodFinder()
            .filterByName("updateWaveHeight").first()
            .createHook {
                after {
                    it.thisObject.objectHelper().setObject("mWaveXOffset", 0)
                }
            }
    }
}