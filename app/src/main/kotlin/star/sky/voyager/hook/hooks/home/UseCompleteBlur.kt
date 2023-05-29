package star.sky.voyager.hook.hooks.home

import android.app.Activity
import android.view.MotionEvent
import com.github.kyuubiran.ezxhelper.ClassUtils.invokeStaticMethodBestMatch
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.callStaticMethod
import star.sky.voyager.utils.api.getObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object UseCompleteBlur : HookRegister() {
    override fun init() = hasEnable("home_use_complete_blur") {
        val blurUtilsClass = loadClass("com.miui.home.launcher.common.BlurUtils")
        val navStubViewClass = loadClass("com.miui.home.recents.NavStubView")
        val applicationClass = loadClass("com.miui.home.launcher.Application")

        blurUtilsClass.methodFinder().first {
            name == "getBlurType"
        }.createHook {
            before {
                it.result = 2
            }
        }
        hasEnable("home_complete_blur_fix") {
            navStubViewClass.methodFinder().first {
                name == "appTouchResolution" && parameterTypes[0] == MotionEvent::class.java
            }.createHook {
                before {
                    val mLauncher = it.thisObject.getObjectField("mLauncher") as Activity?
                    blurUtilsClass.callStaticMethod("fastBlurDirectly", 1.0f, mLauncher?.window)
                }
            }
            hasEnable("recent_blur_for_pad6") {
                navStubViewClass.methodFinder().first {
                    name == "onTouchEvent" && parameterTypes[0] == MotionEvent::class.java
                }.createHook {
                    after {
                        val mLauncher =
                            invokeStaticMethodBestMatch(
                                applicationClass,
                                "getLauncher"
                            ) as Activity
                        invokeStaticMethodBestMatch(
                            blurUtilsClass,
                            "fastBlur",
                            null,
                            1.0f,
                            mLauncher.window,
                            true,
                            500L
                        )
                    }
                }
            }
        }
    }
}