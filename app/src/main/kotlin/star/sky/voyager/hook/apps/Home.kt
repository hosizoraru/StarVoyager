package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam
import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.home.AddFreeformShortcut
import star.sky.voyager.hook.hooks.home.AllowMoveAllWidgetToMinus
import star.sky.voyager.hook.hooks.home.AlwaysBlurWallpaper
import star.sky.voyager.hook.hooks.home.AlwaysShowMiuiWidget
import star.sky.voyager.hook.hooks.home.AlwaysShowStatusBarClock
import star.sky.voyager.hook.hooks.home.AnimDurationRatio
import star.sky.voyager.hook.hooks.home.BackGestureAreaHeight
import star.sky.voyager.hook.hooks.home.BlurWhenOpenFolder
import star.sky.voyager.hook.hooks.home.DisableRecentViewWallpaperDarkening
import star.sky.voyager.hook.hooks.home.DoubleTapToSleep
import star.sky.voyager.hook.hooks.home.DownloadAnimation
import star.sky.voyager.hook.hooks.home.EnablePerfectIcons
import star.sky.voyager.hook.hooks.home.FakeNavBar
import star.sky.voyager.hook.hooks.home.FakeNonDefaultIcon
import star.sky.voyager.hook.hooks.home.FoldDeviceDock
import star.sky.voyager.hook.hooks.home.FolderAnim
import star.sky.voyager.hook.hooks.home.FolderColumnsCount
import star.sky.voyager.hook.hooks.home.HideNavBar
import star.sky.voyager.hook.hooks.home.IconCellCount
import star.sky.voyager.hook.hooks.home.IconCorner
import star.sky.voyager.hook.hooks.home.MonetColor
import star.sky.voyager.hook.hooks.home.MonoChromeIcon
import star.sky.voyager.hook.hooks.home.OptimizeUnlockAnim
import star.sky.voyager.hook.hooks.home.OverlapMode
import star.sky.voyager.hook.hooks.home.PadGestureLine
import star.sky.voyager.hook.hooks.home.PadHideApp
import star.sky.voyager.hook.hooks.home.PadShowMemory
import star.sky.voyager.hook.hooks.home.RealMemory
import star.sky.voyager.hook.hooks.home.RecentViewRemoveCardAnim
import star.sky.voyager.hook.hooks.home.ResizableWidgets
import star.sky.voyager.hook.hooks.home.RestoreGoogleAppIcon
import star.sky.voyager.hook.hooks.home.RestoreGoogleSearch
import star.sky.voyager.hook.hooks.home.RestoreSwitchMinusScreen
import star.sky.voyager.hook.hooks.home.ScrollIconName
import star.sky.voyager.hook.hooks.home.SearchBarXiaoAiCustom
import star.sky.voyager.hook.hooks.home.SetDeviceLevel
import star.sky.voyager.hook.hooks.home.ShortCutTitle
import star.sky.voyager.hook.hooks.home.ShortcutItemCount
import star.sky.voyager.hook.hooks.home.ShowAllApp
import star.sky.voyager.hook.hooks.home.TaskViewCardSize
import star.sky.voyager.hook.hooks.home.UnlockHotSeatIcon
import star.sky.voyager.hook.hooks.home.UseCompleteBlur
import star.sky.voyager.hook.hooks.home.UseTransitionAnimation
import star.sky.voyager.hook.hooks.maxmipad.SetGestureNeedFingerNumTo4
import star.sky.voyager.hook.hooks.multipackage.AospShareSheet
import star.sky.voyager.hook.hooks.multipackage.MaxFreeForm
import star.sky.voyager.utils.init.AppRegister

object Home : AppRegister() {
    override val packageName: String = "com.miui.home"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            AlwaysShowStatusBarClock, // 始终显示桌面时钟
            DoubleTapToSleep, // 双击锁定屏幕
            DisableRecentViewWallpaperDarkening, // 禁用壁纸压暗效果
            RecentViewRemoveCardAnim, // 修改划卡动画
            AddFreeformShortcut, // 快捷菜单添加小窗
            ShowAllApp, // 桌面快捷方式管理显示所有应用
            RestoreGoogleAppIcon, // 恢复Google图标
            RestoreGoogleSearch, // 替换搜索为 Google 搜索
            SearchBarXiaoAiCustom, // 自定义搜索栏的小爱按钮
            ScrollIconName, // 滚动显示应用名称
            RealMemory, // 后台显示实际使用和总内存
            UseTransitionAnimation, // 使用Miui小组件的动画
            AlwaysShowMiuiWidget, // 在安卓小部件页面显示 MIUI 小部件
            ResizableWidgets, // 解除小部件大小限制
            ShortcutItemCount, // 解除Shortcuts数量限制
            UnlockHotSeatIcon, // 解锁底栏图标数量限制
            OptimizeUnlockAnim, // 优化解锁动画
            FakeNavBar, // 移除横屏多任务界面的假小白条
            FakeNonDefaultIcon, // 伪装为非默认图标
            EnablePerfectIcons, // 启用完美图标 // 提升完美图标的优先级
//            IconBreakAnimation, // 优化打断动画
            IconCorner, // 中等图标圆角跟随
            FolderColumnsCount, // 利用整个文件夹视图的宽度 // 文件夹视图内图标的排数
            MonoChromeIcon, // 图标背景色 // 默认颜色 -> 莫奈取色
            IconCellCount, // 解锁图标网格布局
            SetDeviceLevel, // 解除设备限制
            UseCompleteBlur, // 完整模糊 // 完整模糊补全
            AlwaysBlurWallpaper, // 始终模糊桌面壁纸
            OverlapMode, // Fold 样式智能助理
            RestoreSwitchMinusScreen, // 恢复切换负一屏
            AllowMoveAllWidgetToMinus, // 允许将安卓小部件移到负一屏
            FoldDeviceDock, // 折叠屏底栏样式
            AnimDurationRatio, // 自定义动画速度
            TaskViewCardSize, // 自定义最近任务卡片大小
            FolderAnim, // 文件夹动画修改
            BackGestureAreaHeight, // 自定义返回手势区域高度
            PadShowMemory, // 平板显示内存占用
            PadHideApp,
            MaxFreeForm, // 解锁小窗数量限制
            // max mi pad
            SetGestureNeedFingerNumTo4, // 交换手势所需的手指数量-Home部分
            // max mi pad
            BlurWhenOpenFolder, // 文件夹视图模糊
            DownloadAnimation, // 水波纹下载动画
            PadGestureLine,
            HideNavBar,
//            MultipleFreeform,
            AospShareSheet,
        )
    }

    override fun handleInitPackageResources(resparam: InitPackageResourcesParam) {
        autoInitResourcesHooks(
            resparam,
            MonetColor, // 修改成你喜欢的颜色 // 你喜欢的颜色
            ShortCutTitle, // 自定义快捷菜单标题
        )
    }
}

