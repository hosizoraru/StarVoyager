package star.sky.voyager.hook.hooks.packageinstaller

import android.content.Context
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.setBooleanField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object RemovePackageInstallerAds : HookRegister() {
    override fun init() = hasEnable("package_installer_remove_ads") {
        val miuiSettingsCompatClass =
            loadClass("com.android.packageinstaller.compat.MiuiSettingsCompat")
        val qaq = listOf("s", "q", "f", "t", "r")
        miuiSettingsCompatClass.methodFinder()
            .filterByName("isPersonalizedAdEnabled")
            .filterByReturnType(Boolean::class.java)
            .first().createHook {
                after {
                    it.result = false
                }
            }
        miuiSettingsCompatClass.methodFinder()
            .filterByName("isInstallRiskEnabled")
            .filterByParamCount(1)
            .filterByParamTypes(Context::class.java)
            .first().createHook {
                after {
                    it.result = false
                }
            }
        loadClassOrNull("m2.b")?.methodFinder().apply {
            qaq.forEach { qaq ->
                this
                    ?.filterByName(qaq)
                    ?.first()?.createHook {
                        returnConstant(false)
                    }
            }
        }
        var letter = 'a'
        for (i in 0..25) {
            try {
                val classIfExists =
                    loadClassOrNull("com.miui.packageInstaller.ui.listcomponets.${letter}0")
                classIfExists?.let {
                    it.methodFinder()
                        .filterByName("a")
                        .first().createHook {
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