package star.sky.voyager.hook.hooks.systemui

import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable

object NotificationMod : HookRegister() {
    override fun init() {
        val timeSize = getInt("notification_time_size", 130).toFloat()
        val dateSize = getInt("notification_date_size", 41).toFloat()
        val clockSize = getInt("notification_land_clock_size", 37).toFloat()

        val timeColor = Color.parseColor(getString("notification_time_color", "#FFFFFF"))
        val dateColor = Color.parseColor(getString("notification_date_color", "#FFFFFF"))
        val clockColor = Color.parseColor(getString("notification_land_clock_color", "#FFFFFF"))

        val notificationClass =
            loadClass("com.android.systemui.qs.MiuiNotificationHeaderView")
        notificationClass.methodFinder().first {
            name == "updateResources"
        }.createHook {
            after { param ->
                val miuiBigTime = param.thisObject.getObjectFieldAs<TextView>("mBigTime")
                val miuiDate = param.thisObject.getObjectFieldAs<TextView>("mDateView")
                val miuiLandClock = param.thisObject.getObjectFieldAs<TextView>("mLandClock")

                // 修改字体
                hasEnable("notification_time_font") {
                    miuiBigTime.typeface = Typeface.DEFAULT
                }
                hasEnable("notification_date_font") {
                    miuiDate.typeface = Typeface.DEFAULT
                }
                hasEnable("notification_land_clock_font") {
                    miuiLandClock.typeface = Typeface.DEFAULT
                }
                hasEnable("notification_time_bold") {
                    miuiBigTime.typeface = Typeface.DEFAULT_BOLD
                }
                hasEnable("notification_date_bold") {
                    miuiDate.typeface = Typeface.DEFAULT_BOLD
                }
                hasEnable("notification_land_clock_bold") {
                    miuiLandClock.typeface = Typeface.DEFAULT_BOLD
                }

                // 修改字体大小
                miuiBigTime.setTextSize(TypedValue.COMPLEX_UNIT_SHIFT, timeSize)
                miuiDate.setTextSize(TypedValue.COMPLEX_UNIT_SHIFT, dateSize)
                miuiLandClock.setTextSize(TypedValue.COMPLEX_UNIT_SHIFT, clockSize)

                // 修改颜色
                miuiBigTime.setTextColor(timeColor)
                miuiDate.setTextColor(dateColor)
                miuiLandClock.setTextColor(clockColor)

                Log.i("miuiBigTime:  ${miuiBigTime.textSize}  ${miuiBigTime.typeface}  ${miuiBigTime.textColors}")
                Log.i("miuiDateTime:  ${miuiDate.textSize}  ${miuiDate.typeface}  ${miuiDate.textColors}")
                Log.i("miuiLandClock:  ${miuiLandClock.textSize}  ${miuiLandClock.typeface}  ${miuiLandClock.textColors}")
            }
        }
    }
}