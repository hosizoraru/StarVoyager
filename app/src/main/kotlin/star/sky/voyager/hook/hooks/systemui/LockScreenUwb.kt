package star.sky.voyager.hook.hooks.systemui

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Handler
import android.os.Looper
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullAs
import com.github.kyuubiran.ezxhelper.ObjectUtils.setObject
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object LockScreenUwb : HookRegister() {
    override fun init() = hasEnable("lock_screen_uwb") {
        val miuiQuickConnectControllerCls =
            loadClass("com.android.keyguard.negative.MiuiQuickConnectController")
        var cameraManager: CameraManager? = null
        var isFlashlightOn = false
        val torchCallback = object : CameraManager.TorchCallback() {
            override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
                super.onTorchModeChanged(cameraId, enabled)
                isFlashlightOn = enabled
            }
        }
        cameraManager?.registerTorchCallback(torchCallback, Handler(Looper.getMainLooper()))

        miuiQuickConnectControllerCls.methodFinder()
            .filterByName("initXMYZLRes")
            .first().createHook {
                before { param ->
                    setObject(param.thisObject, "mIsSupportXMYZL", true)
                    setObject(param.thisObject, "mIsXMYZLEnable", true)
                }
            }

        miuiQuickConnectControllerCls.methodFinder().filter {
            name in setOf("isUseXMYZLLeft", "isSupportXMYZL")
        }.toList().createHooks {
            returnConstant(true)
        }

        miuiQuickConnectControllerCls.methodFinder()
            .filterByName("launchXMYZLActivity")
            .first().createHook {
//                before { param ->
//                    val myPackageName = "com.miui.securitycenter"
//                    val myActivityName = "com.miui.powercenter.PowerMainActivity"
//
//                    setObject(param.thisObject, "XMYZL_PACKAGE_NAME", myPackageName)
//                    setObject(param.thisObject, "XMYZL_ACTIVITY_NAME", myActivityName)
//                }
                replace { param ->
                    val context = getObjectOrNullAs<Context>(param.thisObject, "mContext")!!
                    cameraManager =
                        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
                    val cameraId = cameraManager!!.cameraIdList[0]

                    isFlashlightOn = !isFlashlightOn
                    if (isFlashlightOn) {
                        cameraManager!!.setTorchMode(cameraId, true) // 打开闪光灯
                    } else {
                        cameraManager!!.setTorchMode(cameraId, false) // 关闭闪光灯
                    }
                }
            }
    }
}