package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XC_MethodReplacement
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.Build.IS_TABLET

object DownloadAnimation : HookRegister() {
    override fun init() = hasEnable("download_animation") {
        when (IS_TABLET) {
            true -> {
                val cpuLevelUtils =
                    loadClass("com.miui.home.launcher.common.CpuLevelUtils")
                val utilities =
                    loadClass("com.miui.home.launcher.common.Utilities")
                // MiPad5Pro
                cpuLevelUtils.methodFinder()
                    .filterByName("needMamlDownload")
                    .first().createHook {
                        returnConstant(true)
                    }
                utilities.methodFinder()
                    .filterByName("isSupportMamlDownload")
                    .first().createHook {
                        returnConstant(true)
                    }
            }

            false -> {
                val deviceLevelUtilsClass =
                    loadClass("com.miui.home.launcher.common.DeviceLevelUtils")
                // n12t k60 k60p 水波纹下载动画
                deviceLevelUtilsClass.methodFinder().filter {
                    name in setOf("needMamlProgressIcon", "needRemoveDownloadAnimationDevice")
                }.toList().createHooks {
                    XC_MethodReplacement.returnConstant(true)
                }
            }
        }
    }
}