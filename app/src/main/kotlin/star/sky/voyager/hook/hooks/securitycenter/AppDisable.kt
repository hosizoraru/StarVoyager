package star.sky.voyager.hook.hooks.securitycenter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.moduleRes
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XposedHelpers.findFirstFieldByExactType
import star.sky.voyager.R
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

@SuppressLint("DiscouragedApi")
object AppDisable : HookRegister() {
    private var mMiuiCoreApps: ArrayList<String>? = null
    private val fail by lazy {
        moduleRes.getString(R.string.fail)
    }
    private val disableAppSettings by lazy {
        moduleRes.getString(R.string.disable_app_settings)
    }
    private val warning by lazy {
        moduleRes.getString(R.string.warning)
    }
    private val disableText by lazy {
        moduleRes.getString(R.string.disable_app_text)
    }

    override fun init() = hasEnable("disable_app_settings") {
        val lpparam = getLoadPackageParam()
        loadClass("com.miui.appmanager.ApplicationsDetailsActivity").methodFinder()
            .filterByName("onCreateOptionsMenu")
            .filterByParamTypes(Menu::class.java)
            .first().createHook {
                after { param ->
                    val act = param.thisObject as Activity
                    val menu = param.args[0] as Menu
                    val dis = menu.add(
                        0,
                        666,
                        1,
                        act.resources.getIdentifier(
                            "app_manager_disable_text",
                            "string",
                            lpparam.packageName
                        )
                    )
                    dis.setIcon(
                        act.resources.getIdentifier(
                            "action_button_stop_svg",
                            "drawable",
                            lpparam.packageName
                        )
                    )
                    dis.setEnabled(true)
                    dis.setShowAsAction(1)

                    val pm = act.packageManager
                    val piField = findFirstFieldByExactType(
                        act.javaClass,
                        PackageInfo::class.java
                    )
                    val mPackageInfo = piField[act] as PackageInfo
                    val appInfo =
                        pm.getApplicationInfo(
                            mPackageInfo.packageName,
                            PackageManager.GET_META_DATA
                        )
                    val isSystem = appInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
                    val isUpdatedSystem =
                        appInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0

                    dis.setTitle(
                        act.resources.getIdentifier(
                            if (appInfo.enabled) "app_manager_disable_text" else "app_manager_enable_text",
                            "string",
                            lpparam.packageName
                        )
                    )

                    mMiuiCoreApps = java.util.ArrayList<String>(
                        listOf(
                            "com.lbe.security.miui",
                            "com.miui.packageinstaller"
                        )
                    )

                    if (mMiuiCoreApps!!.contains(mPackageInfo.packageName)) {
                        dis.setEnabled(false)
                    }

                    if (!appInfo.enabled || isSystem && !isUpdatedSystem) {
                        val item = menu.findItem(2)
                        item?.setVisible(false)
                    }
                }
            }

        loadClass("com.miui.appmanager.ApplicationsDetailsActivity").methodFinder()
            .filterByName("onOptionsItemSelected")
            .filterByParamTypes(MenuItem::class.java)
            .first().createHook {
                after { param ->
                    val item = param.args[0] as MenuItem

                    if (item.itemId == 666) {
                        val act = param.thisObject as Activity
//                        val modRes: Resources = moduleRes
                        val piField = findFirstFieldByExactType(
                            act.javaClass,
                            PackageInfo::class.java
                        )
                        val mPackageInfo = piField[act] as PackageInfo
                        if (mMiuiCoreApps!!.contains(mPackageInfo.packageName)) {
                            makeText(
                                act,
                                disableAppSettings,
                                LENGTH_SHORT
                            ).show()
                            return@after
                        }
                        val pm = act.packageManager
                        val appInfo = pm.getApplicationInfo(
                            mPackageInfo.packageName,
                            PackageManager.GET_META_DATA
                        )
                        val isSystem = appInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
                        val state = pm.getApplicationEnabledSetting(mPackageInfo.packageName)
                        val isEnabledOrDefault =
                            state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED || state == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
                        if (isEnabledOrDefault) {
                            if (isSystem) {
                                val title = warning
                                val text = disableText
                                AlertDialog.Builder(act)
                                    .setTitle(title)
                                    .setMessage(text)
                                    .setPositiveButton(android.R.string.ok) { _, _ ->
                                        setAppState(
                                            act,
                                            mPackageInfo.packageName,
                                            item,
                                            false
                                        )
                                    }
                                    .setNegativeButton(android.R.string.cancel, null)
                                    .show()
                            } else {
                                setAppState(act, mPackageInfo.packageName, item, false)
                            }
//                            setAppState(act, mPackageInfo.packageName, item, false)
                        } else {
                            setAppState(act, mPackageInfo.packageName, item, true)
                        }
                        param.result = true
                    }
                }
            }
    }

    private fun setAppState(act: Activity, pkgName: String, item: MenuItem, enable: Boolean) {
        val pm = act.packageManager
        pm.setApplicationEnabledSetting(
            pkgName,
            if (enable) PackageManager.COMPONENT_ENABLED_STATE_DEFAULT else PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            0
        )
        val state = pm.getApplicationEnabledSetting(pkgName)
        val isEnabledOrDefault =
            state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED || state == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
        if (enable && isEnabledOrDefault || !enable && !isEnabledOrDefault) {
            item.setTitle(
                act.resources.getIdentifier(
                    if (enable) "app_manager_disable_text" else "app_manager_enable_text",
                    "string",
                    "com.miui.securitycenter"
                )
            )
            makeText(
                act,
                act.resources.getIdentifier(
                    if (enable) "app_manager_enabled" else "app_manager_disabled",
                    "string",
                    "com.miui.securitycenter"
                ),
                LENGTH_SHORT
            ).show()
        } else {
            makeText(
                act,
                fail,
                LENGTH_LONG
            ).show()
        }
        Handler().postDelayed({ act.invalidateOptionsMenu() }, 500)
    }
}