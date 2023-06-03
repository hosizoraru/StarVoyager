package star.sky.voyager.hook.hooks.systemui

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.getObjectField
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object HideBatteryIcon : HookRegister() {
    override fun init() {
        loadClass("com.android.systemui.statusbar.views.MiuiBatteryMeterView").methodFinder()
            .filterByName("updateResources")
            .first().createHook {
                after {
                    hasEnable("hide_battery_icon") {
                        (it.thisObject.getObjectFieldAs<ImageView>("mBatteryIconView")).visibility =
                            View.GONE
                        if (it.thisObject.getObjectField("mBatteryStyle") == 1) {
                            (it.thisObject.getObjectFieldAs<FrameLayout>("mBatteryDigitalView")).visibility =
                                View.GONE
                        }
                    }
                    //隐藏电池内的百分比
                    hasEnable("hide_battery_percentage_icon") {
                        (it.thisObject.getObjectFieldAs<TextView>("mBatteryPercentMarkView")).textSize =
                            0F
                    }
                    //隐藏电池百分号
                    hasEnable("hide_battery_percentage_icon") {
                        (it.thisObject.getObjectFieldAs<TextView>("mBatteryPercentMarkView")).textSize =
                            0F
                    }
                }
            }

        loadClass("com.android.systemui.statusbar.views.MiuiBatteryMeterView").methodFinder()
            .filterByName("updateChargeAndText")
            .first().createHook {
                after {
                    hasEnable("hide_battery_charging_icon") {
                        (it.thisObject.getObjectFieldAs<ImageView>("mBatteryChargingInView")).visibility =
                            View.GONE
                        (it.thisObject.getObjectFieldAs<ImageView>("mBatteryChargingView")).visibility =
                            View.GONE
                    }
                }
            }
    }
}