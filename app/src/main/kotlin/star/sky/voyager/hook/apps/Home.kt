package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam
import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.home.AddFreeformShortcut
import star.sky.voyager.hook.hooks.home.AllowMoveAllWidgetToMinus
import star.sky.voyager.hook.hooks.home.AlwaysBlurWallpaper
import star.sky.voyager.hook.hooks.home.AlwaysShowMiuiWidget
import star.sky.voyager.hook.hooks.home.AlwaysShowStatusBarClock
import star.sky.voyager.hook.hooks.home.AnimDurationRatio
import star.sky.voyager.hook.hooks.home.BlurWhenOpenFolder
import star.sky.voyager.hook.hooks.home.DisableRecentViewWallpaperDarkening
import star.sky.voyager.hook.hooks.home.DoubleTapToSleep
import star.sky.voyager.hook.hooks.home.FoldDeviceDock
import star.sky.voyager.hook.hooks.home.FolderAnim
import star.sky.voyager.hook.hooks.home.FolderColumnsCount
import star.sky.voyager.hook.hooks.home.IconCellCount
import star.sky.voyager.hook.hooks.home.IconCorner
import star.sky.voyager.hook.hooks.home.MaxFreeFormH
import star.sky.voyager.hook.hooks.home.ModifyRecentViewRemoveCardAnim
import star.sky.voyager.hook.hooks.home.MonetColor
import star.sky.voyager.hook.hooks.home.MonoChromeIcon
import star.sky.voyager.hook.hooks.home.OptimizeUnlockAnim
import star.sky.voyager.hook.hooks.home.OverlapMode
import star.sky.voyager.hook.hooks.home.RealMemory
import star.sky.voyager.hook.hooks.home.RestoreGoogleAppIcon
import star.sky.voyager.hook.hooks.home.ScrollIconName
import star.sky.voyager.hook.hooks.home.SetDeviceLevel
import star.sky.voyager.hook.hooks.home.ShortcutItemCount
import star.sky.voyager.hook.hooks.home.TaskViewCardSize
import star.sky.voyager.hook.hooks.home.UnlockHotSeatIcon
import star.sky.voyager.hook.hooks.home.UseCompleteBlur
import star.sky.voyager.hook.hooks.home.UseTransitionAnimation
import star.sky.voyager.hook.hooks.maxmipad.GestureOperationHelper
import star.sky.voyager.utils.init.AppRegister

object Home : AppRegister() {
    override val packageName: String = "com.miui.home"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            AlwaysShowStatusBarClock, // 始终显示桌面时钟
            DoubleTapToSleep, // 双击锁定屏幕
            DisableRecentViewWallpaperDarkening, // 禁用壁纸压暗效果
            ModifyRecentViewRemoveCardAnim, // 修改划卡动画
            AddFreeformShortcut, // 快捷菜单添加小窗
            RestoreGoogleAppIcon, // 恢复Google图标
            ScrollIconName, // 滚动显示应用名称
            RealMemory, // 后台显示实际使用和总内存
            UseTransitionAnimation, // 使用Miui小组件的动画
            AlwaysShowMiuiWidget, // 在安卓小部件页面显示 MIUI 小部件
            ShortcutItemCount, // 解除Shortcuts数量限制
            UnlockHotSeatIcon, // 解锁底栏图标数量限制
            OptimizeUnlockAnim, // 优化解锁动画
            IconCorner, // 中等图标圆角跟随
            FolderColumnsCount, // 利用整个文件夹视图的宽度 // 文件夹视图内图标的排数
            MonoChromeIcon, // 图标背景色 // 默认颜色 -> 莫奈取色
            IconCellCount, // 解锁图标网格布局
            SetDeviceLevel, // 解除设备限制
            UseCompleteBlur, // 完整模糊 // 完整模糊补全
            AlwaysBlurWallpaper, // 始终模糊桌面壁纸
            BlurWhenOpenFolder, // 文件夹视图模糊
            OverlapMode, // Fold 样式智能助理
            AllowMoveAllWidgetToMinus, // 允许将安卓小部件移到负一屏
            FoldDeviceDock, // 折叠屏底栏样式
            AnimDurationRatio, // 自定义动画速度
            TaskViewCardSize, // 自定义最近任务卡片大小
            FolderAnim, // 文件夹动画修改
            MaxFreeFormH, // 解锁小窗数量限制
            // max mi pad
            GestureOperationHelper,
            // max mi pad
        )
    }

    override fun handleInitPackageResources(resparam: InitPackageResourcesParam) {
        autoInitResourcesHooks(
            resparam,
            MonetColor, // 修改成你喜欢的颜色 // 你喜欢的颜色
        )
    }
}

