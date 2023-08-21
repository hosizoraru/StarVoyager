package star.sky.voyager.hook.hooks.fileexplorer

import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNullAs
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.yife.XSharedPreferences.getBoolean

object SelectName : HookRegister() {
    private val selectable by lazy {
        getBoolean("file_explorer_can_selectable", false)
    }
    private val singleLine by lazy {
        getBoolean("file_explorer_is_single_line", false)
    }
    private val key by lazy {
        selectable && selectable
    }

    override fun init() {
        if (!key) return
        loadClass("com.android.fileexplorer.view.FileListItem").methodFinder()
            .filterByName("onFinishInflate")
            .first().createHook {
                after {
                    (getObjectOrNullAs<TextView>(
                        it.thisObject,
                        "mFileNameTextView"
                    ))?.apply {
                        setTextIsSelectable(
                            selectable
                        )
                        isSingleLine = singleLine
                    }
                }
            }
    }
}