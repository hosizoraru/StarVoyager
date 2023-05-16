package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.EzXHelper
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XC_MethodHook
import star.sky.voyager.utils.api.findClass
import star.sky.voyager.utils.api.findClassOrNull
import star.sky.voyager.utils.api.hookAfterMethod
import star.sky.voyager.utils.api.hookBeforeMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable

object FolderAnim : HookRegister() {
    override fun init() = hasEnable("home_folder_anim") {
        val value1 = getInt("home_folder_anim_1", 90).toFloat() / 100
        val value2 = getInt("home_folder_anim_2", 30).toFloat() / 100
        val value3 = getInt("home_folder_anim_3", 99).toFloat() / 100
        val value4 = getInt("home_folder_anim_4", 24).toFloat() / 100
        val mSpringAnimator =
            "com.miui.home.launcher.animate.SpringAnimator".findClass(EzXHelper.classLoader)
        var hook1: XC_MethodHook.Unhook? = null
        var hook2: XC_MethodHook.Unhook? = null
        for (i in 47..60) {
            val launcherClass =
                "com.miui.home.launcher.Launcher$$i".findClassOrNull(EzXHelper.classLoader)
            if (launcherClass != null) {
                for (field in launcherClass.declaredFields) {
                    if (field.name == "val\$folderInfo") {
                        launcherClass.methodFinder().first {
                            name == "run"
                        }.createHook {
                            before {
                                hook1 = mSpringAnimator.hookBeforeMethod(
                                    "setDampingResponse",
                                    Float::class.javaPrimitiveType,
                                    Float::class.javaPrimitiveType
                                ) {
                                    it.args[0] = value1
                                    it.args[1] = value2
                                }
                            }
                            after {
                                hook1?.unhook()
                            }
                        }
                        break
                    }
                }
            }
        }

        "com.miui.home.launcher.Launcher".hookBeforeMethod(
            EzXHelper.classLoader,
            "closeFolder",
            Boolean::class.java
        ) {
            if (it.args[0] == true) {
                hook2 = mSpringAnimator.hookBeforeMethod(
                    "setDampingResponse",
                    Float::class.javaPrimitiveType,
                    Float::class.javaPrimitiveType
                ) { hookParam ->
                    hookParam.args[0] = value3
                    hookParam.args[1] = value4
                }
            }
        }
        "com.miui.home.launcher.Launcher".hookAfterMethod(
            EzXHelper.classLoader,
            "closeFolder",
            Boolean::class.java
        ) {
            hook2?.unhook()
        }
    }
}