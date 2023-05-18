package star.sky.voyager.hook.apps

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.widget.Toast
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import star.sky.voyager.utils.init.AppRegister
import star.sky.voyager.utils.key.hasEnable

object Lbe : AppRegister() {
    override val packageName: String = "com.lbe.security.miui"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        hasEnable("hide_miui_clipboard_dialog") {
            val permissionRequestClass =
                XposedHelpers.findClass(
                    "com.lbe.security.sdk.PermissionRequest",
                    lpparam.classLoader
                )

            XposedHelpers.findAndHookMethod("com.lbe.security.ui.SecurityPromptHandler",
                lpparam.classLoader,
                "handleNewRequest",
                permissionRequestClass,
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val permissionRequest = param.args[0]
                        val permission: Long =
                            XposedHelpers.callMethod(permissionRequest, "getPermission") as Long
                        //PermissionManager.PERM_ID_READ_CLIPBOARD
                        if (permission == 274877906944L) {
                            val packageName =
                                XposedHelpers.callMethod(permissionRequest, "getPackage") as String
                            val context =
                                XposedHelpers.getObjectField(
                                    param.thisObject,
                                    "mContext"
                                ) as Context
                            val appName = getAppName(context, packageName)

                            Toast.makeText(
                                context, "$appName 读取了剪贴板",
                                Toast.LENGTH_SHORT
                            ).show()
                            hideDialog(lpparam, packageName, param)

                            XposedBridge.log("$packageName -> $appName 读取了剪贴板")
                        }
                    }
                })
        }
    }

    fun getAppName(context: Context, packageName: String): String {
        val pm: PackageManager = context.applicationContext.packageManager
        val ai: ApplicationInfo = pm.getApplicationInfo(packageName, 0)
        return (pm.getApplicationLabel(ai)) as String
    }

    fun hideDialog(
        lpparam: XC_LoadPackage.LoadPackageParam,
        packageName: String,
        param: XC_MethodHook.MethodHookParam
    ) {
        val clipData = XposedHelpers.findClass(
            "com.lbe.security.utility.AnalyticsHelper",
            lpparam.classLoader
        )
        val hashMap = HashMap<String, String>()
        hashMap["pkgName"] = packageName
        hashMap["count"] = "click"
        XposedHelpers.callStaticMethod(
            clipData,
            "recordCountEvent",
            "clip",
            "ask_allow",
            hashMap
        )

        XposedHelpers.callMethod(param.thisObject, "gotChoice", 3, true, true)
        XposedHelpers.callMethod(param.thisObject, "onStop")
    }
}