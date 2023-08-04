package star.sky.voyager.hook.hooks.securitycenter

import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import io.luckypray.dexkit.enums.MatchType
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.DexKit.safeDexKitBridge

object CameraFaceTracker : HookRegister() {
    override fun init() = hasEnable("camera_face_tracker") {
        safeDexKitBridge.findMethodUsingString {
            usingString = "persist.vendor.camera.facetracker.support"
            matchType = MatchType.FULL
        }.single().getMethodInstance(classLoader).createHook {
            returnConstant(true)
        }
    }
}