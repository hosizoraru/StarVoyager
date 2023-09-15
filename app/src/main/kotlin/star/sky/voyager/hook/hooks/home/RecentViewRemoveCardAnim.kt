package star.sky.voyager.hook.hooks.home

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.view.MotionEvent
import android.view.View
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.callMethod
import star.sky.voyager.utils.api.callStaticMethod
import star.sky.voyager.utils.api.new
import star.sky.voyager.utils.api.replaceMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object RecentViewRemoveCardAnim : HookRegister() {
    override fun init() = hasEnable("home_recent_view_remove_card_animation") {
        val swipeHelperForRecentsCls =
            loadClass("com.miui.home.recents.views.SwipeHelperForRecents")
        val taskStackViewLayoutStyleHorizontalCls =
            loadClass("com.miui.home.recents.TaskStackViewLayoutStyleHorizontal")
        val deviceConfigCls =
            loadClass("com.miui.home.launcher.DeviceConfig")
        val physicBasedInterpolatorCls =
            loadClass("com.miui.home.launcher.anim.PhysicBasedInterpolator")
        val verticalSwipeCls =
            loadClass("com.miui.home.recents.views.VerticalSwipe")

        swipeHelperForRecentsCls.methodFinder()
            .filterByName("onTouchEvent")
            .filterByParamTypes(MotionEvent::class.java)
            .first().createHook {
                after {
                    val mCurrView =
                        it.thisObject.objectHelper()
                            .getObjectOrNullUntilSuperclassAs<View>("mCurrView")
                    mCurrView?.let {
                        mCurrView.alpha *= 0.9f + 0.1f
                        mCurrView.scaleX = 1f
                        mCurrView.scaleY = 1f
                    }
                }
            }

        "com.miui.home.recents.TaskStackViewLayoutStyleHorizontal".replaceMethod(
            classLoader,
            "createScaleDismissAnimation",
            View::class.java,
            Float::class.java
        ) {
            val view =
                it.args[0] as View
            val getScreenHeight =
                deviceConfigCls
                    .callStaticMethod("getScreenHeight") as Int
            val ofFloat = ObjectAnimator.ofFloat(
                view,
                View.TRANSLATION_Y,
                view.translationY,
                -getScreenHeight * 1.1484375f
            )
            val physicBasedInterpolator =
                physicBasedInterpolatorCls.new(0.72f, 0.72f) as TimeInterpolator
            ofFloat.interpolator = physicBasedInterpolator
            ofFloat.duration = 450L
            return@replaceMethod ofFloat
        }

        verticalSwipeCls.methodFinder()
            .filterByName("calculate")
            .filterByParamTypes(Float::class.java)
            .first().createHook {
                after {
                    val f =
                        it.args[0] as Float
                    val asScreenHeightWhenDismiss =
                        verticalSwipeCls
                            .callStaticMethod("getAsScreenHeightWhenDismiss") as Int
                    val f2 =
                        f / asScreenHeightWhenDismiss
                    val mTaskViewHeight =
                        it.thisObject.objectHelper().getObjectOrNullAs<Float>("mTaskViewHeight")
                    val mCurScale =
                        it.thisObject.objectHelper().getObjectOrNullAs<Float>("mCurScale")
                    val f3: Float =
                        mTaskViewHeight!! * mCurScale!!
                    val i =
                        if (f2 > 0.0f) 1 else if (f2 == 0.0f) 0 else -1
                    val afterFrictionValue: Float =
                        it.thisObject.callMethod(
                            "afterFrictionValue", f, asScreenHeightWhenDismiss
                        ) as Float
                    if (i < 0) it.thisObject.objectHelper().setObject(
                        "mCurTransY",
                        (mTaskViewHeight / 2f + afterFrictionValue * 2f) - (f3 / 2f)
                    )
                    it.thisObject.objectHelper().setObject(
                        "mCurAlpha",
                        it.thisObject.objectHelper()
                            .getObjectOrNull("mCurAlpha") as Float * 0.9f + 0.1f
                    )
                }
            }
    }
}