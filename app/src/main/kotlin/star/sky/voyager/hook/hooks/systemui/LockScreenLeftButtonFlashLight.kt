package star.sky.voyager.hook.hooks.systemui

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullAs
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.SetKeyMap.setKeyMap

object LockScreenLeftButtonFlashLight : HookRegister() {
    private var torchStatus = false

    @SuppressLint("ClickableViewAccessibility")
    override fun init() = hasEnable("lock_screen_left_button_flash_light") {
        lateinit var mLeftAffordanceView: ImageView

        loadClass("com.android.systemui.statusbar.phone.KeyguardBottomAreaView").methodFinder()
            .filterByName("onAttachedToWindow")
            .toList().createHooks {
                after { param ->
                    mLeftAffordanceView =
                        getObjectOrNullAs<ImageView>(param.thisObject, "mLeftAffordanceView")!!

                    setKeyMap(
                        mapOf(
                            "flash_light_click" to {
                                mLeftAffordanceView.setOnClickListener { view ->
                                    torchStatus = !torchStatus
                                    val cameraManager =
                                        view.context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
                                    val cameraId = cameraManager.cameraIdList[0]
                                    try {
                                        cameraManager.setTorchMode(cameraId, torchStatus)
                                    } catch (e: CameraAccessException) {
                                        e.printStackTrace()
                                    }
                                }
                            },
                            "flash_light_long_click" to {
                                mLeftAffordanceView.setOnLongClickListener { view: View ->
                                    torchStatus = !torchStatus
                                    val cameraManager =
                                        view.context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
                                    val cameraId = cameraManager.cameraIdList[0]
                                    try {
                                        cameraManager.setTorchMode(cameraId, torchStatus)
                                    } catch (e: CameraAccessException) {
                                        e.printStackTrace()
                                    }
                                    true
                                }
                            },
                            "flash_light_double_click" to {
                                val gestureDetector = GestureDetector(
                                    mLeftAffordanceView.context,
                                    object : GestureDetector.SimpleOnGestureListener() {
                                        override fun onDoubleTap(e: MotionEvent): Boolean {
                                            torchStatus = !torchStatus
                                            val cameraManager =
                                                mLeftAffordanceView.context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
                                            val cameraId = cameraManager.cameraIdList[0]
                                            try {
                                                cameraManager.setTorchMode(cameraId, torchStatus)
                                            } catch (e: CameraAccessException) {
                                                e.printStackTrace()
                                            }
                                            return super.onDoubleTap(e)
                                        }
                                    })

                                mLeftAffordanceView.setOnTouchListener { _, event ->
                                    gestureDetector.onTouchEvent(event)
                                    true
                                }
                            }
                        )
                    )
                }
            }
    }
}