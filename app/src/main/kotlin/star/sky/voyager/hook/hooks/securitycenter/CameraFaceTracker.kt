package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.DexKitS.addUsingStringsEquals
import star.sky.voyager.utils.yife.DexKit.dexKitBridge

object CameraFaceTracker : HookRegister() {
    private val tracker by lazy {
        dexKitBridge.findMethod {
            matcher {
                addUsingStringsEquals("persist.vendor.camera.facetracker.support")
            }
        }.firstOrNull()?.getMethodInstance(classLoader)
    }
    override fun init() = hasEnable("camera_face_tracker") {
//        dexKitBridge.findMethodUsingString {
//            usingString = "persist.vendor.camera.facetracker.support"
//            matchType = MatchType.FULL
//        }.single().getMethodInstance(classLoader).createHook {
//            returnConstant(true)
//        }
        tracker?.createHook {
            returnConstant(true)
        }
    }
}