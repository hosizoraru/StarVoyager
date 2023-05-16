package star.sky.voyager.hook.apps

import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam
import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.hook.hooks.home.AddFreeformShortcut
import star.sky.voyager.hook.hooks.home.AllowMoveAllWidgetToMinus
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
import star.sky.voyager.utils.init.AppRegister

object Home : AppRegister() {
    override val packageName: String = "com.miui.home"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        autoInitHooks(
            lpparam,
            AddFreeformShortcut,
            RestoreGoogleAppIcon,
            MonoChromeIcon,
            MaxFreeFormH,
            ScrollIconName,
            FoldDeviceDock,
            RealMemory,
            AlwaysShowStatusBarClock,
            DoubleTapToSleep,
            DisableRecentViewWallpaperDarkening,
            ModifyRecentViewRemoveCardAnim,
            UseTransitionAnimation,
            OverlapMode,
            AllowMoveAllWidgetToMinus,
            AlwaysShowMiuiWidget,
            ShortcutItemCount,
            UnlockHotSeatIcon,
            OptimizeUnlockAnim,
            IconCorner,
            IconCellCount,
            BlurWhenOpenFolder,
            FolderColumnsCount,
            UseCompleteBlur,
            SetDeviceLevel,
            FolderAnim,
            AnimDurationRatio,
            TaskViewCardSize,
        )
    }

    override fun handleInitPackageResources(resparam: InitPackageResourcesParam) {
        autoInitResourcesHooks(
            resparam,
            MonetColor
        )
    }
}

