package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.providersdownloads.RemoveXlDownload
import star.sky.voyager.utils.init.AppRegister

object ProvidersDownloads : AppRegister() {
    override val packageName: String = "com.android.providers.downloads"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            RemoveXlDownload, // 移除在根目录创建的.xldownload文件夹
        )
    }
}