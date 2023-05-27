package star.sky.voyager.hook.hooks.systemui

import android.graphics.Typeface
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object LockScreenFont : HookRegister() {
    override fun init() {
        loadClass("com.miui.clock.MiuiBaseClock").methodFinder()
            .filterByName("updateViewsTextSize")
            .toList().createHooks {
                after { param ->
                    hasEnable("lock_screen_clock_use_system_font") {
                        val mTimeText = param.thisObject.getObjectFieldAs<TextView>("mTimeText")
                        mTimeText.typeface = Typeface.DEFAULT
                    }
                }
            }
        loadClass("com.miui.clock.MiuiLeftTopLargeClock").methodFinder()
            .filter {
                name == "onLanguageChanged" && parameterTypes[0] == String::class.java
            }.toList().createHooks {
                after { param ->
                    hasEnable("lock_screen_date_use_system_font") {
                        val mTimeText =
                            param.thisObject.getObjectFieldAs<TextView>("mCurrentDateLarge")
                        mTimeText.typeface = Typeface.DEFAULT
                    }
                }
            }
    }
}