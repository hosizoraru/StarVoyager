package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.gallery.HDREnhance
import star.sky.voyager.hook.hooks.gallery.IdPhoto
import star.sky.voyager.hook.hooks.gallery.MagicMatting
import star.sky.voyager.hook.hooks.gallery.MagicSky
import star.sky.voyager.hook.hooks.gallery.Ocr
import star.sky.voyager.hook.hooks.gallery.OcrForm
import star.sky.voyager.hook.hooks.gallery.Pdf
import star.sky.voyager.hook.hooks.gallery.PhotoMovie
import star.sky.voyager.hook.hooks.gallery.Remover2
import star.sky.voyager.hook.hooks.gallery.TextYanHua
import star.sky.voyager.hook.hooks.gallery.VideoEditor
import star.sky.voyager.hook.hooks.gallery.VideoPost
import star.sky.voyager.hook.hooks.multipackage.SuperClipboard
import star.sky.voyager.utils.init.AppRegister

object Gallery : AppRegister() {
    override val packageName: String = "com.miui.gallery"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            HDREnhance, // 超动态显示
            SuperClipboard, // 超级剪切板
            Ocr, // 解锁提取文字
            OcrForm, // 解锁提取表格
            Pdf, // 解锁生成pdf
            PhotoMovie, // 解锁照片电影
            IdPhoto, // 解锁证件照
            VideoPost, // 解锁视频特效
            VideoEditor, // 解锁Mi剪辑
            TextYanHua, // 解锁文字烟花
            Remover2, // 解锁魔法消除
            MagicMatting, // 解锁魔法抠图
            MagicSky, // 解锁魔法换天
        )
    }
}