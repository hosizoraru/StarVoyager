package star.sky.voyager.hook.hooks.home

import android.app.Activity
import android.view.MotionEvent
import android.view.View
import com.github.kyuubiran.ezxhelper.EzXHelper
import star.sky.voyager.utils.api.callMethod
import star.sky.voyager.utils.api.callStaticMethod
import star.sky.voyager.utils.api.callStaticMethodOrNull
import star.sky.voyager.utils.api.findClass
import star.sky.voyager.utils.api.getObjectField
import star.sky.voyager.utils.api.hookAfterAllMethods
import star.sky.voyager.utils.api.hookAfterMethod
import star.sky.voyager.utils.api.hookBeforeAllMethods
import star.sky.voyager.utils.api.hookBeforeMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object BlurWhenOpenFolder : HookRegister() {
    override fun init() = hasEnable("home_blur_when_open_folder") {
        val isUserBlurWhenOpenFolder =
            "com.miui.home.launcher.common.BlurUtils".findClass(EzXHelper.classLoader)
                .callStaticMethodOrNull("isUserBlurWhenOpenFolder")
        if (isUserBlurWhenOpenFolder != null) {
            "com.miui.home.launcher.common.BlurUtils".hookAfterMethod(
                EzXHelper.classLoader,
                "isUserBlurWhenOpenFolder"
            ) {
                it.result = true
            }
        } else {
            var isShouldBlur = false
            val folderInfo = "com.miui.home.launcher.FolderInfo".findClass(EzXHelper.classLoader)
            val launcherClass = "com.miui.home.launcher.Launcher".findClass(EzXHelper.classLoader)
            val blurUtilsClass =
                "com.miui.home.launcher.common.BlurUtils".findClass(EzXHelper.classLoader)
            val navStubViewClass =
                "com.miui.home.recents.NavStubView".findClass(EzXHelper.classLoader)
            val cancelShortcutMenuReasonClass =
                "com.miui.home.launcher.shortcuts.CancelShortcutMenuReason".findClass(EzXHelper.classLoader)

            launcherClass.hookAfterMethod("openFolder", folderInfo, View::class.java) {
                val mLauncher = it.thisObject as Activity
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
                val mLauncher = it.thisObject as Activity
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
                val mLauncher = it.thisObject as Activity
                if (isShouldBlur) blurUtilsClass.callStaticMethod(
                    "fastBlur",
                    1.0f,
                    mLauncher.window,
                    true,
                    0L
                )
            }

            launcherClass.hookBeforeMethod("onGesturePerformAppToHome") {
                val mLauncher = it.thisObject as Activity
                if (isShouldBlur) {
                    blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
                }
            }

            blurUtilsClass.hookBeforeAllMethods("fastBlurWhenStartOpenOrCloseApp") {
                val mLauncher = it.args[1] as Activity
                val isInEditing = mLauncher.callMethod("isInEditing") as Boolean
                if (isShouldBlur) it.result =
                    blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
                else if (isInEditing) it.result =
                    blurUtilsClass.callStaticMethod("fastBlur", 1.0f, mLauncher.window, true, 0L)
            }

            blurUtilsClass.hookBeforeAllMethods("fastBlurWhenFinishOpenOrCloseApp") {
                val mLauncher = it.args[0] as Activity
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
                val mLauncher = it.args[0] as Activity
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
                    navStubViewClass.hookBeforeMethod(
                        "appTouchResolution",
                        MotionEvent::class.java
                    ) {
                        val mLauncher = it.thisObject.getObjectField("mLauncher") as Activity?
                        if (isShouldBlur) {
                            blurUtilsClass.callStaticMethod(
                                "fastBlurDirectly",
                                1.0f,
                                mLauncher?.window
                            )
                        }
                    }
                }
            }
        }
    }
}