package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object IconPosition : HookRegister() {
    override fun init() {
        val leftIcon1 =
            loadClass("com.android.systemui.statusbar.phone.MiuiDripLeftStatusBarIconControllerImpl")
        leftIcon1.methodFinder()
            .filterByName("setIconVisibility")
            .filterByParamCount(2)
            .first().createHook {
                before {
                    when (it.args[0] as String) {
                        // 移除左侧闹钟
                        "alarm_clock" -> hasEnable("at_right_alarm_icon") {
                            it.args[1] = false
                        }
                        // 移除左侧NFC
                        "nfc" -> hasEnable("at_right_nfc_icon") {
                            it.args[1] = false
                        }
                        // 移除左侧声音
                        "volume" -> hasEnable("at_right_volume_icon") {
                            it.args[1] = false
                        }
                        // 移除左侧勿扰
                        "zen" -> hasEnable("at_right_zen_icon") {
                            it.args[1] = false
                        }
                    }
                }
            }

        loadClass("com.android.systemui.statusbar.phone.StatusBarIconController\$IconManager").methodFinder()
            .filterByName("setBlockList")
            .filterByParamTypes(MutableList::class.java)
            .first().createHook {
                before {
                    if (it.args[0] != null) {
                        val rightBlockList = it.args[0] as MutableList<*>
                        // 显示右侧闹钟
                        hasEnable("at_right_alarm_icon") {
                            rightBlockList.remove("alarm_clock")
                        }
                        // 显示右侧NFC
                        hasEnable("at_right_nfc_icon") {
                            rightBlockList.remove("nfc")
                        }
                        // 显示右侧声音
                        hasEnable("at_right_volume_icon") {
                            rightBlockList.remove("volume")
                        }
                        // 显示右侧勿扰
                        hasEnable("at_right_zen_icon") {
                            rightBlockList.remove("zen")
                        }
                    }
                }
            }
    }
}