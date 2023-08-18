package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.guardprovider.Anti2
import star.sky.voyager.utils.init.AppRegister

object GuardProvider : AppRegister() {
    override val packageName: String = "com.miui.guardprovider"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
//        AntiDefraudAppManager().handleLoadPackage(lpparam) // 阻止上传应用列表
        autoInitHooks(
            lpparam,
            Anti2,
        )
    }
}