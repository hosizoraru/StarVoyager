package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.weather.HighAnimation
import star.sky.voyager.hook.hooks.weather.MoreCard
import star.sky.voyager.utils.init.AppRegister

object Weather : AppRegister() {
    override val packageName: String = "com.miui.weather2"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            HighAnimation, // 动画等级为高
            MoreCard,
        )
    }
}