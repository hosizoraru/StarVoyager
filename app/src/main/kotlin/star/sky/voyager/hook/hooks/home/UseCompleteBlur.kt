package star.sky.voyager.hook.hooks.home

import android.app.Activity
import android.view.MotionEvent
import com.github.kyuubiran.ezxhelper.ClassUtils.invokeStaticMethodBestMatch
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.callStaticMethod
import star.sky.voyager.utils.api.getObjectField
import star.sky.voyager.utils.api.hookBeforeMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getBoolean
import star.sky.voyager.utils.key.hasEnable

object UseCompleteBlur : HookRegister() {
    override fun init() = hasEnable("home_use_complete_blur") {
        val blurUtilsClass = loadClass("com.miui.home.launcher.common.BlurUtils")
        val navStubViewClass = loadClass("com.miui.home.recents.NavStubView")
        val applicationClass = loadClass("com.miui.home.launcher.Application")

        blurUtilsClass.methodFinder()
            .filterByName("getBlurType")
            .first().createHook {
                returnConstant(2)
            }
        hasEnable("home_complete_blur_fix") {
            if (getBoolean("home_recent_view_wallpaper_darkening", false)) {
                navStubViewClass.hookBeforeMethod("updateDimLayerAlpha", Float::class.java) {
                    val mLauncher = applicationClass.callStaticMethod("getLauncher") as Activity
                    val value = 1 - it.args[0] as Float
                    if (value != 1f) {
                        blurUtilsClass.callStaticMethod("fastBlurDirectly", value, mLauncher.window)
                    }
                }
            } else {
                navStubViewClass.methodFinder()
                    .filterByName("appTouchResolution")
                    .filterByParamTypes(MotionEvent::class.java)
                    .first().createHook {
                        before {
                            val mLauncher = it.thisObject.getObjectField("mLauncher") as Activity?
                            invokeStaticMethodBestMatch(
                                blurUtilsClass,
                                "fastBlurDirectly",
                                null,
                                1.0f,
                                mLauncher?.window
                            )
                        }
                    }
            }
            hasEnable("recent_blur_for_pad6") {
                navStubViewClass.methodFinder()
                    .filterByName("onTouchEvent")
                    .filterByParamTypes(MotionEvent::class.java)
                    .first().createHook {
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