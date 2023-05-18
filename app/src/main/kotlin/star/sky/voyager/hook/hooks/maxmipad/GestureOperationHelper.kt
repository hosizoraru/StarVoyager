package star.sky.voyager.hook.hooks.maxmipad

import android.view.MotionEvent
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.MemberExtensions.paramCount
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.field
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object GestureOperationHelper : HookRegister() {
    override fun init() = hasEnable("set_gesture_need_finger_num_to_4") {
        val clazzGestureOperationHelper =
            loadClass("com.miui.home.recents.GestureOperationHelper")
        clazzGestureOperationHelper.methodFinder().first {
            name == "isThreePointerSwipeLeftOrRightInScreen" &&
                    paramCount == 2 &&
                    parameterTypes[0] == MotionEvent::class.java &&
                    parameterTypes[1] == Int::class.java
        }.createHook {
            before { param ->
                val motionEvent = param.args[0] as MotionEvent
                val swipeFlag = param.args[1] as Int
                val flagSwipeLeft = clazzGestureOperationHelper.field("SWIPE_DIRECTION_LEFT", true)
                    .getInt(null)
                val flagSwipeRight =
                    clazzGestureOperationHelper.field("SWIPE_DIRECTION_RIGHT", true)
                        .getInt(null)
                val flagsSwipeLeftAndRight = setOf(flagSwipeLeft, flagSwipeRight)
                val z = if (motionEvent.device == null) {
                    true
                } else {
                    motionEvent.device.sources and 4098 == 4098
                }
                param.result =
                    true && z && (swipeFlag in flagsSwipeLeftAndRight) && motionEvent.pointerCount == 4
            }
        }
    }
}