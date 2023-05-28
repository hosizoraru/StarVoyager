package star.sky.voyager.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R
import star.sky.voyager.activity.pages.apps.AndroidPage
import star.sky.voyager.activity.pages.apps.AppManagerPage
import star.sky.voyager.activity.pages.apps.GalleryPage
import star.sky.voyager.activity.pages.apps.HomePage
import star.sky.voyager.activity.pages.apps.MaxMiPadPage
import star.sky.voyager.activity.pages.apps.MiAiPage
import star.sky.voyager.activity.pages.apps.PowerKeeperPage
import star.sky.voyager.activity.pages.apps.SecurityPage
import star.sky.voyager.activity.pages.apps.SmartHubPage
import star.sky.voyager.activity.pages.apps.SystemUIPage
import star.sky.voyager.activity.pages.main.AboutPage
import star.sky.voyager.activity.pages.main.MainPage
import star.sky.voyager.activity.pages.main.MenuPage
import star.sky.voyager.activity.pages.sub.ControlCenterPage
import star.sky.voyager.activity.pages.sub.DisableFixedOrientationPage
import star.sky.voyager.activity.pages.sub.GalleryUnlockPage
import star.sky.voyager.activity.pages.sub.HideIconPage
import star.sky.voyager.activity.pages.sub.HomeBlurPage
import star.sky.voyager.activity.pages.sub.HomeModPage
import star.sky.voyager.activity.pages.sub.IconPositionPage
import star.sky.voyager.activity.pages.sub.LockScreenPage
import star.sky.voyager.activity.pages.sub.NotificationCenterPage
import star.sky.voyager.activity.pages.sub.PersonalAssistantPage
import star.sky.voyager.utils.key.BackupUtils

class MainActivity : MIUIActivity() {
    private val activity = this
    override fun onCreate(savedInstanceState: Bundle?) {
        if (!checkLSPosed()) isLoad = false
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("WorldReadableFiles")
    private fun checkLSPosed(): Boolean {
        try {
            @Suppress("DEPRECATION")
            setSP(getSharedPreferences("voyager_config", MODE_WORLD_READABLE))
            return true
        } catch (exception: SecurityException) {
            isLoad = true
            MIUIDialog(this) {
                setTitle(R.string.warning)
                setMessage(R.string.not_support)
                setCancelable(false)
                setRButton(R.string.done) {
//                    exitProcess(0)
                    dismiss()
                }
            }.show()
            return true
        }
    }

    init {
        Companion.activity = this
        registerPage(MainPage::class.java)
        registerPage(MenuPage::class.java)
        registerPage(AboutPage::class.java)
        registerPage(SystemUIPage::class.java)
        registerPage(AndroidPage::class.java)
        registerPage(HomePage::class.java)
        registerPage(SecurityPage::class.java)
        registerPage(PowerKeeperPage::class.java)
        registerPage(AppManagerPage::class.java)
        registerPage(GalleryPage::class.java)
        registerPage(MiAiPage::class.java)
        registerPage(SmartHubPage::class.java)
        registerPage(MaxMiPadPage::class.java)
        registerPage(DisableFixedOrientationPage::class.java)
        registerPage(HideIconPage::class.java)
        registerPage(IconPositionPage::class.java)
        registerPage(PersonalAssistantPage::class.java)
        registerPage(HomeBlurPage::class.java)
        registerPage(HomeModPage::class.java)
        registerPage(GalleryUnlockPage::class.java)
        registerPage(NotificationCenterPage::class.java)
        registerPage(ControlCenterPage::class.java)
        registerPage(LockScreenPage::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null && resultCode == RESULT_OK) {
            when (requestCode) {
                BackupUtils.CREATE_DOCUMENT_CODE -> {
                    BackupUtils.handleCreateDocument(activity, data.data)
                }

                BackupUtils.OPEN_DOCUMENT_CODE -> {
                    BackupUtils.handleReadDocument(activity, data.data)
                }
            }
        }
    }
}