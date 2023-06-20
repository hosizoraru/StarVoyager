package star.sky.voyager.hook.hooks.corepatch

import android.util.Log
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge.hookAllConstructors
import de.robv.android.xposed.XposedHelpers.callMethod
import de.robv.android.xposed.XposedHelpers.findClassIfExists
import de.robv.android.xposed.XposedHelpers.setBooleanField
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.lang.reflect.InvocationTargetException


open class CorePatchForT : CorePatchForSv2() {
    @Throws(
        IllegalAccessException::class,
        InvocationTargetException::class,
        InstantiationException::class
    )
    override fun handleLoadPackage(loadPackageParam: XC_LoadPackage.LoadPackageParam) {
        super.handleLoadPackage(loadPackageParam)
        findAndHookMethod(
            "com.android.server.pm.PackageManagerServiceUtils", loadPackageParam.classLoader,
            "checkDowngrade",
            "com.android.server.pm.parsing.pkg.AndroidPackage",
            "android.content.pm.PackageInfoLite",
            ReturnConstant(prefs, "down_grade", null)
        )
        val signingDetails = getSigningDetails(loadPackageParam.classLoader)
        //New package has a different signature
        //处理覆盖安装但签名不一致
        hookAllMethods(signingDetails, "checkCapability", object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                // Don't handle PERMISSION (grant SIGNATURE permissions to pkgs with this cert)
                // Or applications will have all privileged permissions
                // https://cs.android.com/android/platform/superproject/+/master:frameworks/base/core/java/android/content/pm/PackageParser.java;l=5947?q=CertCapabilities
                if (param.args[1] as Int != 4 && prefs.getBoolean("digestCreak", true)) {
                    param.result = true
                }
            }
        })

        // Package " + packageName + " signatures do not match previously installed version; ignoring!"
        // public boolean checkCapability(String sha256String, @CertCapabilities int flags) {
        // public boolean checkCapability(SigningDetails oldDetails, @CertCapabilities int flags)
        hookAllMethods(
            "android.content.pm.PackageParser",
            loadPackageParam.classLoader,
            "checkCapability",
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    Log.e("CorePatch", "checkCapability")
                    // Don't handle PERMISSION (grant SIGNATURE permissions to pkgs with this cert)
                    // Or applications will have all privileged permissions
                    // https://cs.android.com/android/platform/superproject/+/master:frameworks/base/core/java/android/content/pm/PackageParser.java;l=5947?q=CertCapabilities
                    if (prefs.getBoolean("auth_creak", true)) {
                        if (param.args[1] as Int != 4) {
                            param.result = true
                        }
                    }
                }
            })
        if (prefs.getBoolean("digestCreak", true) && prefs.getBoolean("UsePreSig", false)) {
            findAndHookMethod("com.android.server.pm.InstallPackageHelper",
                loadPackageParam.classLoader,
                "doesSignatureMatchForPermissions",
                String::class.java,
                "com.android.server.pm.parsing.pkg.ParsedPackage",
                Int::class.javaPrimitiveType,
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        //If we decide to crack this then at least make sure they are same apks, avoid another one that tries to impersonate.
                        if (param.result == false) {
                            val pPname =
                                callMethod(param.args[1], "getPackageName") as String
                            if (pPname.contentEquals(param.args[0] as String)) {
                                param.result = true
                            }
                        }
                    }
                })
        }
        findAndHookMethod("com.android.server.pm.ScanPackageUtils",
            loadPackageParam.classLoader,
            "assertMinSignatureSchemeIsValid",
            "com.android.server.pm.parsing.pkg.AndroidPackage",
            Int::class.javaPrimitiveType,
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    if (prefs.getBoolean("auth_creak", true)) {
                        param.result = null
                    }
                }
            })
        val strictJarVerifier =
            findClass("android.util.jar.StrictJarVerifier", loadPackageParam.classLoader)
        if (strictJarVerifier != null) {
            hookAllConstructors(strictJarVerifier, object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    if (prefs.getBoolean("auth_creak", true)) {
                        setBooleanField(
                            param.thisObject,
                            "signatureSchemeRollbackProtectionsEnforced",
                            false
                        )
                    }
                }
            })
        }
    }

    override fun getSigningDetails(classLoader: ClassLoader?): Class<*> {
        return findClassIfExists("android.content.pm.SigningDetails", classLoader)
    }
}

