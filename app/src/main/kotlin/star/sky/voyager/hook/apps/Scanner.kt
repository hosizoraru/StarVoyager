package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.android.AospShareSheet
import star.sky.voyager.hook.hooks.scanner.Card
import star.sky.voyager.hook.hooks.scanner.DocPpt
import star.sky.voyager.hook.hooks.scanner.Document
import star.sky.voyager.hook.hooks.scanner.Excel
import star.sky.voyager.hook.hooks.scanner.Ocr
import star.sky.voyager.hook.hooks.scanner.Ppt
import star.sky.voyager.hook.hooks.scanner.Translation
import star.sky.voyager.utils.init.AppRegister

object Scanner : AppRegister() {
    override val packageName: String = "com.xiaomi.scanner"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            Card, // 解锁扫名片
            DocPpt, // 解锁扫文件
            Document, // 解锁扫文档
            Excel, // 解锁扫提取表格
            Ocr, // 解锁识文字
            Ppt, // 解锁提取ppt
            Translation, // 解锁翻译
            AospShareSheet,
        )
    }
}