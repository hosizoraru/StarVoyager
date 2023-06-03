package star.sky.voyager.hook.hooks.systemui

import android.util.TypedValue
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getFloat

object BatteryPercentage : HookRegister() {
    override fun init() {
        val size = getFloat("battery_percentage_font_size", 0f)
        if (size == 0f) return
        loadClass("com.android.systemui.statusbar.views.MiuiBatteryMeterView").methodFinder()
            .filterByName("updateResources")
            .first().createHook {
                after {
                    (it.thisObject.getObjectFieldAs<TextView>("mBatteryPercentView")).setTextSize(
                        TypedValue.COMPLEX_UNIT_DIP, size
                    )
                }
            }
    }
}