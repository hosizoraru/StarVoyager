package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.scanner.EnableCard
import star.sky.voyager.hook.hooks.scanner.EnableDocPpt
import star.sky.voyager.hook.hooks.scanner.EnableDocument
import star.sky.voyager.hook.hooks.scanner.EnableExcel
import star.sky.voyager.hook.hooks.scanner.EnableOcr
import star.sky.voyager.hook.hooks.scanner.EnablePpt
import star.sky.voyager.hook.hooks.scanner.EnableTranslation
import star.sky.voyager.utils.init.AppRegister

object Scanner : AppRegister() {
    override val packageName: String = "com.xiaomi.scanner"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            EnableCard, // 解锁扫名片
            EnableDocPpt, // 解锁扫文件
            EnableDocument, // 解锁扫文档
            EnableExcel, // 解锁扫提取表格
            EnableOcr, // 解锁识文字
            EnablePpt, // 解锁提取ppt
            EnableTranslation, // 解锁翻译
        )
    }
}