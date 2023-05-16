package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.gallery.EnableHDREnhance
import star.sky.voyager.hook.hooks.gallery.EnableIdPhoto
import star.sky.voyager.hook.hooks.gallery.EnableMagicMatting
import star.sky.voyager.hook.hooks.gallery.EnableMagicSky
import star.sky.voyager.hook.hooks.gallery.EnableOcr
import star.sky.voyager.hook.hooks.gallery.EnableOcrForm
import star.sky.voyager.hook.hooks.gallery.EnablePdf
import star.sky.voyager.hook.hooks.gallery.EnablePhotoMovie
import star.sky.voyager.hook.hooks.gallery.EnableRemover2
import star.sky.voyager.hook.hooks.gallery.EnableTextYanHua
import star.sky.voyager.hook.hooks.gallery.EnableVideoEditor
import star.sky.voyager.hook.hooks.gallery.EnableVideoPost
import star.sky.voyager.utils.init.AppRegister

object Gallery : AppRegister() {
    override val packageName: String = "com.miui.gallery"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            EnableHDREnhance, // 超动态显示
            EnableOcr, // 解锁提取文字
            EnableOcrForm, // 解锁提取表格
            EnablePdf, // 解锁生成pdf
            EnablePhotoMovie, // 解锁照片电影
            EnableIdPhoto, // 解锁证件照
            EnableVideoPost, // 解锁视频特效
            EnableVideoEditor, // 解锁Mi剪辑
            EnableTextYanHua, // 解锁文字烟花
            EnableRemover2, // 解锁魔法消除
            EnableMagicMatting, // 解锁魔法抠图
            EnableMagicSky, // 解锁魔法换天
        )
    }
}