package star.sky.voyager.hook.hooks.maxmipad

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object BaseMiuiMultiFingerGesture : HookRegister() {
    override fun init() = hasEnable("set_gesture_need_finger_num_to_4") {
        loadClass("com.miui.server.input.gesture.multifingergesture.gesture.BaseMiuiMultiFingerGesture").methodFinder()
            .first {
                name == "getFunctionNeedFingerNum"
            }.createHook {
            returnConstant(4)
        }
    }
}