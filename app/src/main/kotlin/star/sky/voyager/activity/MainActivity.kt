package star.sky.voyager.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.dialog.MIUIDialog
import star.sky.voyager.R
import star.sky.voyager.activity.pages.AboutPage
import star.sky.voyager.activity.pages.AndroidPage
import star.sky.voyager.activity.pages.AppManagerPage
import star.sky.voyager.activity.pages.DisableFixedOrientationPage
import star.sky.voyager.activity.pages.GalleryPage
import star.sky.voyager.activity.pages.HideIconPage
import star.sky.voyager.activity.pages.HomePage
import star.sky.voyager.activity.pages.IconPositionPage
import star.sky.voyager.activity.pages.MainPage
import star.sky.voyager.activity.pages.MaxMiPadPage
import star.sky.voyager.activity.pages.MenuPage
import star.sky.voyager.activity.pages.MiAiPage
import star.sky.voyager.activity.pages.PowerKeeperPage
import star.sky.voyager.activity.pages.SecurityPage
import star.sky.voyager.activity.pages.SmartHubPage
import star.sky.voyager.activity.pages.SystemUIPage
import star.sky.voyager.utils.key.BackupUtils
import kotlin.system.exitProcess

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
            isLoad = false
            MIUIDialog(this) {
                setTitle(R.string.warning)
                setMessage(R.string.not_support)
                setCancelable(false)
                setRButton(R.string.done) {
                    exitProcess(0)
                }
            }.show()
            return false
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