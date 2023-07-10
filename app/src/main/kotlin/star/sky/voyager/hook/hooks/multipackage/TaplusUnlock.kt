package star.sky.voyager.hook.hooks.multipackage

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.setStaticObject
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.SettingsFeatures.SettingsFeaturesCls
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
//                hasEnable("unlock_taplus") {
//                    IS_INTERNATIONAL_BUILD.apply {
//                        setStaticObject(loadClass("miui.os.Build"), "IS_INTERNATIONAL_BUILD", false)
//                    }
//
//                    loadClass("miuix.pickerwidget.widget.NumberPicker").methodFinder()
//                        .filterByName("isInternationalBuild")
//                        .first().createHook {
//                            returnConstant(false)
//                        }
//
//                    loadClass("com.xiaomi.onetrack.Configuration").methodFinder()
//                        .filterByName("isInternational")
//                        .first().createHook {
//                            returnConstant(false)
//                        }
//                }

                hasEnable("unlock_taplus_for_pad") {
                    if (!IS_TABLET) return@hasEnable
                    loadClass("com.miui.contentextension.setting.activity.MainSettingsActivity")
                        .methodFinder()
                        .filterByName("getFragment")
                        .first().createHook {
                            setStaticObject(
                                loadClass("miui.os.Build"),
                                "IS_TABLET",
                                false
                            )
                        }
                }
            }
        }
    }
}