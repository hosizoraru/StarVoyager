package star.sky.voyager.hook.hooks.packageinstaller

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.setBooleanField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object RemovePackageInstallerAds : HookRegister() {
    override fun init() = hasEnable("package_installer_remove_ads") {
        val miuiSettingsCompatClass =
            loadClassOrNull("com.android.packageinstaller.compat.MiuiSettingsCompat")!!

        try {
            miuiSettingsCompatClass.methodFinder()
                .filterByName("isPersonalizedAdEnabled")
                .filterByReturnType(Boolean::class.java)
                .toList().createHooks {
                    before {
                        it.result = false
                    }
                }
        } catch (_: Throwable) {

        }

        var letter = 'a'
        for (i in 0..25) {
            try {
                val classIfExists =
                    loadClassOrNull("com.miui.packageInstaller.ui.listcomponets.${letter}0")
                classIfExists?.let {
                    it.methodFinder().filterByName("a").first().createHook {
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
