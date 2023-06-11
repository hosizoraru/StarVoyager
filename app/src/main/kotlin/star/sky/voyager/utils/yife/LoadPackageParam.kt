package star.sky.voyager.utils.yife

import android.os.Build
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.io.File

object LoadPackageParam {
    fun XC_LoadPackage.LoadPackageParam.getAppVersionCode() = runCatching {
        if (packageName == "android") {
            Build.VERSION.SDK_INT
        } else {
            val parser = loadClass("android.content.pm.PackageParser").newInstance()
            val apkPath = File(appInfo.sourceDir)
            val pkg = parser.objectHelper().invokeMethodBestMatch("parsePackage", null, apkPath, 0)
            pkg?.objectHelper()?.getObjectOrNullAs<Int>("mVersionCode") ?: 0
        }
    }.getOrDefault(0)

    fun XC_LoadPackage.LoadPackageParam.getAppVersionName() = runCatching {
        if (packageName == "android") {
            Build.VERSION.RELEASE_OR_CODENAME
        } else {
            val parser = loadClass("android.content.pm.PackageParser").newInstance()
            val apkPath = File(appInfo.sourceDir)
            val pkg = parser.objectHelper().invokeMethodBestMatch("parsePackage", null, apkPath, 0)
            pkg?.objectHelper()?.getObjectOrNullAs<String>("mVersionName") ?: "Error: Unknown"
        }
    }.getOrDefault("Error: Unknown")
}
