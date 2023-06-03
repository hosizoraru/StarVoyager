package star.sky.voyager.hook.hooks.systemui

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object HideSimIcon : HookRegister() {
    override fun init() {
        loadClass("com.android.systemui.statusbar.phone.StatusBarSignalPolicy").methodFinder()
            .filterByName("hasCorrectSubs")
            .filterByParamTypes(MutableList::class.java)
            .first().createHook {
                before {
                    val list = it.args[0] as MutableList<*>
                    val size = list.size
                    hasEnable("hide_sim_two_icon", extraCondition = { size == 2 }) {
                        list.removeAt(1)
                    }
                    hasEnable("hide_sim_one_icon", extraCondition = { size >= 1 }) {
                        list.removeAt(0)
                    }
                }
            }
    }
}