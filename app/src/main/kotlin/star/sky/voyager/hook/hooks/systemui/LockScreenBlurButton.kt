package star.sky.voyager.hook.hooks.systemui

import android.app.KeyguardManager
import android.content.Context
import android.graphics.Color.argb
import android.graphics.drawable.LayerDrawable
import android.view.View
import android.widget.ImageView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullAs
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.BlurDraw.createBlurDrawable

object LockScreenBlurButton : HookRegister() {
    override fun init() = hasEnable("blur_lock_screen_button") {
        lateinit var mLeftAffordanceView: ImageView
        lateinit var mRightAffordanceView: ImageView
        lateinit var keyguardBottomAreaView: View

        loadClassOrNull(
            "com.android.systemui.statusbar.phone.KeyguardBottomAreaView"
        )!!.methodFinder()
            .filter {
                name in setOf(
                    "onAttachedToWindow",
                    "onDetachedFromWindow",
                    "updateRightAffordanceIcon",
                    "updateLeftAffordanceIcon"
                )
            }.toList().createHooks {
                after { param ->
                    mLeftAffordanceView =
                        getObjectOrNullAs<ImageView>(param.thisObject, "mLeftAffordanceView")!!

                    mRightAffordanceView =
                        getObjectOrNullAs<ImageView>(param.thisObject, "mRightAffordanceView")!!

                    keyguardBottomAreaView = param.thisObject as View

                    // Your blur logic
                    val context = keyguardBottomAreaView.context ?: return@after
                    val keyguardManager =
                        context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

                    if (keyguardManager.isKeyguardLocked) {
                        val leftBlurDrawable = createBlurDrawable(
                            keyguardBottomAreaView, 40, 100, argb(60, 255, 255, 255)
                        )
                        val leftLayerDrawable = LayerDrawable(arrayOf(leftBlurDrawable))
                        val rightBlurDrawable = createBlurDrawable(
                            keyguardBottomAreaView, 40, 100, argb(60, 255, 255, 255)
                        )
                        val rightLayerDrawable = LayerDrawable(arrayOf(rightBlurDrawable))
                        leftLayerDrawable.setLayerInset(0, 40, 40, 40, 40)
                        rightLayerDrawable.setLayerInset(0, 40, 40, 40, 40)
                        mLeftAffordanceView.background = leftLayerDrawable
                        mRightAffordanceView.background = rightLayerDrawable
                    } else {
                        mLeftAffordanceView.background = null
                        mRightAffordanceView.background = null
                    }
                }
            }
    }
}
