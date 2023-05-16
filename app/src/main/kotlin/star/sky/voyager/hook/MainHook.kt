package star.sky.voyager.hook

import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.XSharedPreferences
import de.robv.android.xposed.callbacks.XC_InitPackageResources
import star.sky.voyager.BuildConfig
import star.sky.voyager.hook.apps.Aireco
import star.sky.voyager.hook.apps.Android
import star.sky.voyager.hook.apps.Cast
import star.sky.voyager.hook.apps.ExternalStorage
import star.sky.voyager.hook.apps.FileExplorer
import star.sky.voyager.hook.apps.Gallery
import star.sky.voyager.hook.apps.Home
import star.sky.voyager.hook.apps.Market
import star.sky.voyager.hook.apps.MediaEditor
import star.sky.voyager.hook.apps.MiSettings
import star.sky.voyager.hook.apps.MiShare
import star.sky.voyager.hook.apps.PowerKeeper
import star.sky.voyager.hook.apps.Scanner
import star.sky.voyager.hook.apps.ScreenRecorder
import star.sky.voyager.hook.apps.SecurityCenter
import star.sky.voyager.hook.apps.Settings
import star.sky.voyager.hook.apps.SystemUI
import star.sky.voyager.hook.apps.TaPlus
import star.sky.voyager.utils.init.AppRegister
import star.sky.voyager.utils.init.EasyXposedInit

val PACKAGE_NAME_HOOKED = listOf(
    "com.miui.gallery",
    "com.miui.screenrecorder",
    "com.miui.home",
    "com.android.systemui",
    "com.xiaomi.aireco",
    "com.xiaomi.market",
    "com.xiaomi.scanner",
    "com.miui.mishare.connectivity",
    "com.milink.service",
    "com.miui.mediaeditor",
    "com.miui.powerkeeper",
    "com.xiaomi.misettings",
    "com.android.settings",
    "com.android.fileexplorer",
    "com.android.externalstorage",
    "com.miui.contentextension",
    "com.miui.securitycenter",
    "com.xiaomi.scanner",
)

class MainHook : EasyXposedInit() {
    private var prefs = XSharedPreferences(BuildConfig.APPLICATION_ID, "voyager_config")

    override val registeredApp: List<AppRegister> = listOf(
        Aireco, // 小爱建议
        Android, // 系统框架
        Cast, // 投屏
        ExternalStorage, // 外部存储服务
        FileExplorer, // 文件管理
        Gallery, // 相册
        Home, // 系统桌面
        Market, // 应用商店
        MediaEditor, // 小米相册-编辑
        MiSettings, // 小米设置
        MiShare, // 小米互传
        PowerKeeper, // 电量与性能
        Scanner, // 小爱视觉
        ScreenRecorder, // 屏幕录制
        SecurityCenter, // 手机管家
        Settings, // 设置
        SystemUI, // 系统界面
        TaPlus, // 传送门
    )

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam?) {
        if (prefs.getBoolean("main_switch", true)) {
            super.initZygote(startupParam)
        }
    }

    override fun handleInitPackageResources(resparam: XC_InitPackageResources.InitPackageResourcesParam?) {
        if (prefs.getBoolean("main_switch", true)) {
            super.handleInitPackageResources(resparam)
        }
    }
}