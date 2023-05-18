package star.sky.voyager.hook.hooks.packageinstaller

import android.content.Context
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.MemberExtensions.paramCount
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.findClassOrNull
import star.sky.voyager.utils.api.setBooleanField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object RemovePackageInstallerAds : HookRegister() {
    override fun init() = hasEnable("package_installer_remove_ads") {
        loadClass("com.android.packageinstaller.compat.MiuiSettingsCompat").methodFinder().first {
            name == "isPersonalizedAdEnabled" && returnType == Boolean::class.java
        }.createHook {
            after {
                it.result = false
            }
        }
        loadClass("com.android.packageinstaller.compat.MiuiSettingsCompat").methodFinder().first {
            name == "isInstallRiskEnabled" &&
                    paramCount == 1 &&
                    parameterTypes[0] == Context::class.java
        }.createHook {
            after {
                it.result = false
            }
        }
        val qaq = listOf("s", "q", "f", "t", "r")
        loadClass("m2.b").methodFinder().apply {
            qaq.forEach { qaq ->
                first {
                    name == qaq
                }.createHook {
                    returnConstant(false)
                }
            }
        }
        var letter = 'a'
        for (i in 0..25) {
            try {
                val classIfExists =
                    "com.miui.packageInstaller.ui.listcomponets.${letter}0".findClassOrNull(
                        EzXHelper.classLoader
                    )
                classIfExists?.let {
                    it.methodFinder().first {
                        name == "a"
                    }.createHook {
                        after { hookParam ->
                            hookParam.thisObject.setBooleanField("l", false)
                        }
                    }
                }
            } catch (t: Throwable) {
                letter++
            }
        }
    }
}