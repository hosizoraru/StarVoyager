package star.sky.voyager.hook.hooks.packageinstaller

import android.view.View
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.callMethodOrNullAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object RemovePackageInstallerAds : HookRegister() {
    override fun init() = hasEnable("package_installer_remove_ads") {
        val miuiSettingsCompatClass =
            loadClassOrNull("com.android.packageinstaller.compat.MiuiSettingsCompat")
        val mSafeModeTipViewObjectCls =
            loadClassOrNull("com.miui.packageInstaller.ui.listcomponets.SafeModeTipViewObject")
        val mSafeModeTipViewObjectViewHolderCls =
            loadClassOrNull("com.miui.packageInstaller.ui.listcomponets.SafeModeTipViewObject\$ViewHolder")

        runCatching {
            miuiSettingsCompatClass!!.methodFinder()
                .filterByName("isPersonalizedAdEnabled")
                .filterByReturnType(Boolean::class.java)
                .toList().createHooks {
                    returnConstant(false)
                }
        }

        runCatching {
            mSafeModeTipViewObjectCls!!.methodFinder()
                .filterByParamTypes(mSafeModeTipViewObjectViewHolderCls)
                .toList().createHooks {
                    after {
                        it.args[0].callMethodOrNullAs<View>("getClContentView")?.visibility =
                            View.GONE
                    }
                }
        }
    }
}
