package star.sky.voyager.hook.hooks.multipackage

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.MiuiBuildCls
import star.sky.voyager.utils.voyager.LazyClass.SettingsFeaturesCls
import star.sky.voyager.utils.yife.Build.IS_TABLET

object TaplusUnlock : HookRegister() {
    override fun init() {
        when (hostPackageName) {
            "com.android.settings" -> {
                hasEnable("unlock_taplus") {
                    SettingsFeaturesCls.methodFinder()
                        .filterByName("isNeedRemoveContentExtension")
                        .first().createHook {
                            returnConstant(false)
                        }
                }
            }

            "com.miui.contentextension" -> {
                hasEnable("unlock_taplus_for_pad") {
                    if (!IS_TABLET) return@hasEnable
                    loadClass("com.miui.contentextension.setting.activity.MainSettingsActivity")
                        .methodFinder()
                        .filterByName("getFragment")
                        .first().createHook {
                            setStaticObject(
                                MiuiBuildCls,
                                "IS_TABLET",
                                false
                            )
                        }
                }
            }
        }
    }
}