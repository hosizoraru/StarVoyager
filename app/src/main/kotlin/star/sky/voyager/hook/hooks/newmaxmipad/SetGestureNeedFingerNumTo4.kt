package star.sky.voyager.hook.hooks.newmaxmipad

import android.view.MotionEvent
import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.EzXHelper
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object SetGestureNeedFingerNumTo4 : HookRegister() {
    override fun init() = hasEnable("set_gesture_need_finger_num_to_4") {
        when (EzXHelper.hostPackageName) {
            "android" -> {
                ClassUtils.loadClass("com.miui.server.input.gesture.multifingergesture.gesture.BaseMiuiMultiFingerGesture")
                    .methodFinder()
                    .filterByName("getFunctionNeedFingerNum").first().createHook {
                        returnConstant(4)
                    }
            }

            "com.miui.home" -> {
                val clazzGestureOperationHelper =
                    ClassUtils.loadClass("com.miui.home.recents.GestureOperationHelper")
                clazzGestureOperationHelper.methodFinder()
                    .filterByName("isThreePointerSwipeLeftOrRightInScreen")
                    .filterByParamTypes(MotionEvent::class.java, Int::class.java).first()
                    .createHook {
                        before { param ->
                            val motionEvent = param.args[0] as MotionEvent
                            val swipeFlag = param.args[1] as Int
                            val flagSwipeLeft =
                                ClassUtils.getStaticObjectOrNullAs<Int>(
                                    clazzGestureOperationHelper,
                                    "SWIPE_DIRECTION_LEFT"
                                )
                            val flagSwipeRight =
                                ClassUtils.getStaticObjectOrNullAs<Int>(
                                    clazzGestureOperationHelper,
                                    "SWIPE_DIRECTION_RIGHT"
                                )
                            val flagsSwipeLeftAndRight = setOf(flagSwipeLeft, flagSwipeRight)
                            val z =
                                if (motionEvent.device == null) true
                                else motionEvent.device.sources and 4098 == 4098
                            param.result =
                                z && (swipeFlag in flagsSwipeLeftAndRight) && motionEvent.pointerCount == 4
                        }
                    }
            }
        }
    }
}