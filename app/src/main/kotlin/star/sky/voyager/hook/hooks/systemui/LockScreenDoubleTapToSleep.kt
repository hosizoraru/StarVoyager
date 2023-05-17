package star.sky.voyager.hook.hooks.systemui

import android.app.KeyguardManager
import android.content.Context
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XposedHelpers
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import kotlin.math.abs

object LockScreenDoubleTapToSleep : HookRegister() {
    override fun init() = hasEnable("lock_screen_double_tap_to_sleep") {
        loadClass("com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer").methodFinder()
            .first {
                name == "onFinishInflate"
            }.createHook {
            before {
                val view = it.thisObject as View
                XposedHelpers.setAdditionalInstanceField(view, "currentTouchTime", 0L)
                XposedHelpers.setAdditionalInstanceField(view, "currentTouchX", 0f)
                XposedHelpers.setAdditionalInstanceField(view, "currentTouchY", 0f)
                view.setOnTouchListener(View.OnTouchListener { v, event ->
                    if (event.action != MotionEvent.ACTION_DOWN) return@OnTouchListener false
                    var currentTouchTime =
                        XposedHelpers.getAdditionalInstanceField(view, "currentTouchTime") as Long
                    var currentTouchX =
                        XposedHelpers.getAdditionalInstanceField(view, "currentTouchX") as Float
                    var currentTouchY =
                        XposedHelpers.getAdditionalInstanceField(view, "currentTouchY") as Float
                    val lastTouchTime = currentTouchTime
                    val lastTouchX = currentTouchX
                    val lastTouchY = currentTouchY
                    currentTouchTime = System.currentTimeMillis()
                    currentTouchX = event.x
                    currentTouchY = event.y
                    if (currentTouchTime - lastTouchTime < 250L && abs(currentTouchX - lastTouchX) < 100f && abs(
                            currentTouchY - lastTouchY
                        ) < 100f
                    ) {
                        val keyguardMgr =
                            v.context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
                        if (keyguardMgr.isKeyguardLocked) {
                            XposedHelpers.callMethod(
                                v.context.getSystemService(Context.POWER_SERVICE),
                                "goToSleep",
                                SystemClock.uptimeMillis()
                            )
                        }
                        currentTouchTime = 0L
                        currentTouchX = 0f
                        currentTouchY = 0f
                    }
                    XposedHelpers.setAdditionalInstanceField(
                        view,
                        "currentTouchTime",
                        currentTouchTime
                    )
                    XposedHelpers.setAdditionalInstanceField(view, "currentTouchX", currentTouchX)
                    XposedHelpers.setAdditionalInstanceField(view, "currentTouchY", currentTouchY)
                    v.performClick()
                    false
                })
            }
        }
    }
}