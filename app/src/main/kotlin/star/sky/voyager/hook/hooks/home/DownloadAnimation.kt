package star.sky.voyager.hook.hooks.home

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XC_MethodReplacement
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object DownloadAnimation : HookRegister() {
    override fun init() = hasEnable("download_animation") {
        val deviceLevelUtilsClass =
            ClassUtils.loadClass("com.miui.home.launcher.common.DeviceLevelUtils")
        // n12t k60 k60p 水波纹下载动画
        deviceLevelUtilsClass.methodFinder()
            .filterByName("needMamlProgressIcon")
            .first().createHook {
                XC_MethodReplacement.returnConstant(true)
            }
        deviceLevelUtilsClass.methodFinder()
            .filterByName("needRemoveDownloadAnimationDevice")
            .first().createHook {
                XC_MethodReplacement.returnConstant(false)
            }
    }
}