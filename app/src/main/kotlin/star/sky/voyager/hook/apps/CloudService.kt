package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.cloudservice.Widevine
import star.sky.voyager.utils.init.AppRegister

object CloudService : AppRegister() {
    override val packageName: String = "com.miui.cloudservice"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            Widevine, // 解锁WidevineL1界面
        )
    }
}