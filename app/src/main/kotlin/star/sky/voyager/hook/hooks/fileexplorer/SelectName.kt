package star.sky.voyager.hook.hooks.fileexplorer

import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.getObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.yife.XSharedPreferences.getBoolean

object SelectName : HookRegister() {
    override fun init() {
        hasEnable("file_explorer_can_selectable") {
            hasEnable("file_explorer_is_single_line") {
                loadClass("com.android.fileexplorer.view.FileListItem").methodFinder().first {
                    name == "onFinishInflate"
                }.createHook {
                    after {
                        (it.thisObject.getObjectField("mFileNameTextView") as TextView).apply {
                            setTextIsSelectable(getBoolean("file_explorer_can_selectable", false))
                            isSingleLine = getBoolean("file_explorer_is_single_line", false)
                        }
                    }
                }
            }
        }
    }
}