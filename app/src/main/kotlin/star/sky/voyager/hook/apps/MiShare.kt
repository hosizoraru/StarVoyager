package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.mishare.NoAutoTurnOff
import star.sky.voyager.utils.init.AppRegister

object MiShare : AppRegister() {
    override val packageName: String = "com.miui.mishare.connectivity"

    //    var versionCode: Int = -1
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
//        versionCode = lpparam.getAppVersionCode()
        autoInitHooks(
            lpparam,
            NoAutoTurnOff, // 禁止小米互传自动关闭
        )
    }
}