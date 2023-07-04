package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.fileexplorer.SelectName
import star.sky.voyager.hook.hooks.multipackage.SuperClipboard
import star.sky.voyager.utils.init.AppRegister

object FileExplorer : AppRegister() {
    override val packageName: String = "com.android.fileexplorer"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            SelectName, // 文件夹名字可以选择操作 // 文件夹名字以单行显示
            SuperClipboard, // 超级剪切板
        )
    }
}