package star.sky.voyager.hook.hooks.home

import android.app.Activity
import android.view.MotionEvent
import com.github.kyuubiran.ezxhelper.EzXHelper
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.callStaticMethod
import star.sky.voyager.utils.api.findClass
import star.sky.voyager.utils.api.getObjectField
import star.sky.voyager.utils.api.hookBeforeMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object UseCompleteBlur : HookRegister() {
    override fun init() = hasEnable("home_use_complete_blur") {
        val blurUtilsClass =
            "com.miui.home.launcher.common.BlurUtils".findClass(EzXHelper.classLoader)
        val navStubViewClass = "com.miui.home.recents.NavStubView".findClass(EzXHelper.classLoader)

        blurUtilsClass.methodFinder().first {
            name == "getBlurType"
        }.createHook {
            before {
                it.result = 2
            }
        }
        hasEnable("home_complete_blur_fix") {
            navStubViewClass.hookBeforeMethod("appTouchResolution", MotionEvent::class.java) {
                val mLauncher = it.thisObject.getObjectField("mLauncher") as Activity?
                blurUtilsClass.callStaticMethod("fastBlurDirectly", 1.0f, mLauncher?.window)
            }
        }
    }
}