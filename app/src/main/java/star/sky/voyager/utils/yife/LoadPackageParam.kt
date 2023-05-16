package star.sky.voyager.utils.yife

import android.os.Build
import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.paramTypes
import com.github.kyuubiran.ezxhelper.params
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.io.File

object LoadPackageParam {
    fun XC_LoadPackage.LoadPackageParam.getAppVersionCode(): Int {
        try {
            if (this.packageName == "android") return Build.VERSION.SDK_INT
            val parser = ClassUtils.loadClass("android.content.pm.PackageParser").newInstance()
            val apkPath = File(this.appInfo.sourceDir)
            parser.objectHelper {
                invokeMethod(
                    methodName = "parsePackage",
                    paramTypes = paramTypes(File::class.java, Int::class.java),
                    params = params(apkPath, 0)
                )?.let {
                    return it.javaClass.getField("mVersionCode").getInt(it)
                }
            }
            return 0
        } catch (e: Throwable) {
            return 0
        }
    }

    fun XC_LoadPackage.LoadPackageParam.getAppVersionName(): String {
        try {
            if (this.packageName == "android") {
                return Build.VERSION.RELEASE_OR_CODENAME
            }
            val parser = ClassUtils.loadClass("android.content.pm.PackageParser").newInstance()
            val apkPath = File(this.appInfo.sourceDir)
            parser.objectHelper {
                invokeMethod(
                    methodName = "parsePackage",
                    paramTypes = paramTypes(File::class.java, Int::class.java),
                    params = params(apkPath, 0)
                )?.let {
                    return it.javaClass.getField("mVersionName").get(it) as String
                }
            }
            return "Error: Unknown"
        } catch (e: Throwable) {
            return "Error: Unknown"
        }
    }

}