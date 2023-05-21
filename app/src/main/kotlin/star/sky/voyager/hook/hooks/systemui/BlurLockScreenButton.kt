package star.sky.voyager.hook.hooks.systemui

import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.view.View
import android.widget.ImageView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.createBlurDrawable
import star.sky.voyager.utils.api.getValueByField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object BlurLockScreenButton : HookRegister() {
    override fun init() = hasEnable("blur_lock_screen_button") {
        loadClassOrNull(
            "com.android.systemui.statusbar.phone.KeyguardBottomAreaView"
        )?.methodFinder()?.forEach { method ->
            if (method.name == "onAttachedToWindow") {
                method.createHook {
                    after { param ->
                        val mLeftAffordanceView = getValueByField(
                            param.thisObject,
                            "mLeftAffordanceView"
                        ) as ImageView
                        val mRightAffordanceView = getValueByField(
                            param.thisObject,
                            "mRightAffordanceView"
                        ) as ImageView

                        val keyguardBottomAreaView = param.thisObject as View
                        val leftBlurDrawable = createBlurDrawable(
                            keyguardBottomAreaView,
                            40,
                            100,
                            Color.argb(60, 255, 255, 255)
                        )
                        val leftLayerDrawable = LayerDrawable(arrayOf(leftBlurDrawable))
                        val rightBlurDrawable = createBlurDrawable(
                            keyguardBottomAreaView,
                            40,
                            100,
                            Color.argb(60, 255, 255, 255)
                        )
                        val rightLayerDrawable = LayerDrawable(arrayOf(rightBlurDrawable))
                        leftLayerDrawable.setLayerInset(0, 40, 40, 40, 40)
                        rightLayerDrawable.setLayerInset(0, 40, 40, 40, 40)
                        mLeftAffordanceView.background = leftLayerDrawable
                        mRightAffordanceView.background = rightLayerDrawable
                    }
                }
            }
        }
    }
}