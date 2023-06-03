package star.sky.voyager.hook.hooks.systemui

import android.app.KeyguardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.view.View
import android.widget.ImageView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.createBlurDrawable
import star.sky.voyager.utils.api.getValueByField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import java.lang.ref.WeakReference
import java.util.Timer
import java.util.TimerTask

object BlurLockScreenButton : HookRegister() {
    private var mLeftAffordanceView: WeakReference<ImageView>? = null
    private var mRightAffordanceView: WeakReference<ImageView>? = null
    private var keyguardBottomAreaView: WeakReference<View>? = null

    override fun init() = hasEnable("blur_lock_screen_button") {
        loadClassOrNull(
            "com.android.systemui.statusbar.phone.KeyguardBottomAreaView"
        )?.methodFinder()
            ?.filterByName("onAttachedToWindow")
            ?.toList()?.createHooks {
                after { param ->
                    mLeftAffordanceView = WeakReference(
                        getValueByField(
                            param.thisObject,
                            "mLeftAffordanceView"
                        ) as ImageView
                    )
                    mRightAffordanceView = WeakReference(
                        getValueByField(
                            param.thisObject,
                            "mRightAffordanceView"
                        ) as ImageView
                    )
                    keyguardBottomAreaView = WeakReference(param.thisObject as View)
                }
            }

        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val context = keyguardBottomAreaView?.get()?.context ?: return
                val keyguardManager =
                    context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

                if (keyguardManager.isKeyguardLocked) {
                    val leftBlurDrawable = createBlurDrawable(
                        keyguardBottomAreaView!!.get()!!,
                        40,
                        100,
                        Color.argb(60, 255, 255, 255)
                    )
                    val leftLayerDrawable = LayerDrawable(arrayOf(leftBlurDrawable))
                    val rightBlurDrawable = createBlurDrawable(
                        keyguardBottomAreaView!!.get()!!,
                        40,
                        100,
                        Color.argb(60, 255, 255, 255)
                    )
                    val rightLayerDrawable = LayerDrawable(arrayOf(rightBlurDrawable))
                    leftLayerDrawable.setLayerInset(0, 40, 40, 40, 40)
                    rightLayerDrawable.setLayerInset(0, 40, 40, 40, 40)
                    mLeftAffordanceView?.get()?.background = leftLayerDrawable
                    mRightAffordanceView?.get()?.background = rightLayerDrawable
                } else {
                    mLeftAffordanceView?.get()?.background = null
                    mRightAffordanceView?.get()?.background = null
                }
            }
        }, 0, 100)
    }
}
