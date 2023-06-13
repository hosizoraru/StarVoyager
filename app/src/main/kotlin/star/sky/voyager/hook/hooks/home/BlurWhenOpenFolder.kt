package star.sky.voyager.hook.hooks.home

import android.app.Activity
import android.view.MotionEvent
import android.view.View
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import star.sky.voyager.utils.api.callMethod
import star.sky.voyager.utils.api.callStaticMethod
import star.sky.voyager.utils.api.hookAfterAllMethods
import star.sky.voyager.utils.api.hookAfterMethod
import star.sky.voyager.utils.api.hookBeforeAllMethods
import star.sky.voyager.utils.api.hookBeforeMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getBoolean
import star.sky.voyager.utils.key.hasEnable

object BlurWhenOpenFolder : HookRegister() {
    override fun init() = hasEnable("home_blur_when_open_folder") {
        val folderInfo = loadClass("com.miui.home.launcher.FolderInfo")
        val launcherClass = loadClass("com.miui.home.launcher.Launcher")
        val blurUtilsClass = loadClass("com.miui.home.launcher.common.BlurUtils")
        val navStubViewClass = loadClass("com.miui.home.recents.NavStubView")
        val cancelShortcutMenuReasonClass =
            loadClass("com.miui.home.launcher.shortcuts.CancelShortcutMenuReason")
        val applicationClass =
            loadClass("com.miui.home.launcher.Application")

        try {
            launcherClass.hookBeforeMethod("isShouldBlur") {
                it.result = false
            }
            blurUtilsClass.hookBeforeMethod(
                "fastBlurWhenOpenOrCloseFolder",
                launcherClass,
                Boolean::class.java
            ) {
                it.result = null
            }
        } catch (_: Exception) {
        }
        var isShouldBlur = false


        launcherClass.hookAfterMethod("openFolder", folderInfo, View::class.java) {
            val mLauncher = applicationClass.callStaticMethod("getLauncher") as Activity
            val isInNormalEditing = mLauncher.callMethod("isInNormalEditing") as Boolean
            if (!isInNormalEditing) blurUtilsClass.callStaticMethod(
                "fastBlur",
                1.0f,
                mLauncher.window,
                true
            )
        }

        launcherClass.hookAfterMethod("isFolderShowing") {
            isShouldBlur = it.result as Boolean
        }

        launcherClass.hookAfterMethod("closeFolder", Boolean::class.java) {
            isShouldBlur = false
            val mLauncher = applicationClass.callStaticMethod("getLauncher") as Activity
            val isInNormalEditing = mLauncher.callMethod("isInNormalEditing") as Boolean
            if (isInNormalEditing) blurUtilsClass.callStaticMethod(
                "fastBlur",
                1.0f,
                mLauncher.window,
                true,
                0L
            )
            else blurUtilsClass.callStaticMethod("fastBlur", 0.0f, mLauncher.window, true)
        }

        launcherClass.hookAfterMethod(
            "cancelShortcutMenu",
            Int::class.java,
            cancelShortcutMenuReasonClass
        ) {
            val mLauncher = applicationClass.callStaticMethod("getLauncher") as Activity
            if (isShouldBlur) blurUtilsClass.callStaticMethod(
                "fastBlur",
                1.0f,
                mLauncher.window,
                true,
                0L
            )
        }

        launcherClass.hookBeforeMethod("onGesturePerformAppToHome") {
            val mLauncher = applicationClass.callStaticMethod("getLauncher") as Activity
            if (isShouldBlur) {
                blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            }
        }

        blurUtilsClass.hookBeforeAllMethods("fastBlurWhenStartOpenOrCloseApp") {
            val mLauncher = applicationClass.callStaticMethod("getLauncher") as Activity
            val isInEditing = mLauncher.callMethod("isInEditing") as Boolean
            if (isShouldBlur) it.result =
                blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            else if (isInEditing) it.result =
                blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
        }

        blurUtilsClass.hookBeforeAllMethods("fastBlurWhenFinishOpenOrCloseApp") {
            val mLauncher = applicationClass.callStaticMethod("getLauncher") as Activity
            val isInEditing = mLauncher.callMethod("isInEditing") as Boolean
            if (isShouldBlur) it.result =
                blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            else if (isInEditing) it.result =
                blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
        }

        blurUtilsClass.hookAfterAllMethods("fastBlurWhenEnterRecents") {
            it.args[0]?.callMethod("hideShortcutMenuWithoutAnim")
        }

        blurUtilsClass.hookAfterAllMethods("fastBlurWhenExitRecents") {
            val mLauncher = applicationClass.callStaticMethod("getLauncher") as Activity
            val isInEditing = mLauncher.callMethod("isInEditing") as Boolean
            if (isShouldBlur) it.result =
                blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            else if (isInEditing) it.result =
                blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
        }

        blurUtilsClass.hookBeforeAllMethods("fastBlurDirectly") {
            val blurRatio = it.args[0] as Float
            if (isShouldBlur && blurRatio == 0.0f) it.result = null
        }

        hasEnable("home_use_complete_blur") {
            hasEnable("home_complete_blur_fix") {
                if (getBoolean("home_recent_view_wallpaper_darkening", false)) {
                    navStubViewClass.hookBeforeMethod("updateDimLayerAlpha", Float::class.java) {
                        val mLauncher = applicationClass.callStaticMethod("getLauncher") as Activity
                        val value = 1 - it.args[0] as Float
                        if (value != 1f) {
                            blurUtilsClass.callStaticMethod(
                                "fastBlurDirectly",
                                value,
                                mLauncher.window
                            )
                        }
                    }
                } else {
                    navStubViewClass.hookBeforeMethod(
                        "appTouchResolution",
                        MotionEvent::class.java
                    ) {
                        val mLauncher = applicationClass.callStaticMethod("getLauncher") as Activity
                        blurUtilsClass.callStaticMethod("fastBlurDirectly", 1.0f, mLauncher.window)
                    }
                }
            }
        }
    }
}