package star.sky.voyager.hook.hooks.securitycenter

import android.app.Activity
import android.app.AlertDialog
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.moduleRes
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XposedHelpers
import star.sky.voyager.R
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object AppDisable : HookRegister() {
    var mMiuiCoreApps: ArrayList<String>? = null

    override fun init() = hasEnable("disable_app_settings") {
        val lpparam = getLoadPackageParam()
        loadClass("com.miui.appmanager.ApplicationsDetailsActivity").methodFinder().first {
            name == "onCreateOptionsMenu" && parameterTypes[0] == Menu::class.java
        }.createHook {
            after { param ->
                val act = param.thisObject as Activity
                val menu = param.args.get(0) as Menu
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
                val piField = XposedHelpers.findFirstFieldByExactType(
                    act.javaClass,
                    PackageInfo::class.java
                )
                val mPackageInfo = piField[act] as PackageInfo
                val appInfo =
                    pm.getApplicationInfo(mPackageInfo.packageName, PackageManager.GET_META_DATA)
                val isSystem = appInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
                val isUpdatedSystem = appInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0

                dis.setTitle(
                    act.resources.getIdentifier(
                        if (appInfo.enabled) "app_manager_disable_text" else "app_manager_enable_text",
                        "string",
                        lpparam.packageName
                    )
                )

                mMiuiCoreApps = java.util.ArrayList<String>(
                    listOf<String>(
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

        loadClass("com.miui.appmanager.ApplicationsDetailsActivity").methodFinder().first {
            name == "onOptionsItemSelected" && parameterTypes[0] == MenuItem::class.java
        }.createHook {
            after { param ->
                val item = param.args[0] as MenuItem

                if (item.itemId == 666) {
                    val act = param.thisObject as Activity
                    val modRes: Resources = moduleRes
                    val piField = XposedHelpers.findFirstFieldByExactType(
                        act.javaClass,
                        PackageInfo::class.java
                    )
                    val mPackageInfo = piField[act] as PackageInfo
                    if (mMiuiCoreApps!!.contains(mPackageInfo.packageName)) {
                        Toast.makeText(
                            act,
                            modRes.getString(R.string.disable_app_settings),
                            Toast.LENGTH_SHORT
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
                            val title = modRes.getString(R.string.warning)
                            val text = modRes.getString(R.string.disable_app_text)
                            AlertDialog.Builder(act)
                                .setTitle(title)
                                .setMessage(text)
                                .setPositiveButton(android.R.string.ok) { dialog, which ->
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
            Toast.makeText(
                act,
                act.resources.getIdentifier(
                    if (enable) "app_manager_enabled" else "app_manager_disabled",
                    "string",
                    "com.miui.securitycenter"
                ),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                act,
                moduleRes.getString(R.string.fail),
                Toast.LENGTH_LONG
            ).show()
        }
        Handler().postDelayed({ act.invalidateOptionsMenu() }, 500)
    }
}