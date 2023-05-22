package star.sky.voyager.hook.hooks.home

import android.view.ViewGroup
import android.widget.GridView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable

object FolderColumnsCount : HookRegister() {
    override fun init() {
        val value = getInt("home_folder_columns", 3)
        if (value == 3) return
        loadClass("com.miui.home.launcher.Folder").methodFinder().filter {
            name == "bind"
        }.toList().createHooks {
            after {
                val columns: Int = value
                val mContent = it.thisObject.getObjectFieldAs<GridView>("mContent")
                mContent.numColumns = columns
                hasEnable("home_folder_width") {
                    if (columns > 3) {
                        mContent.setPadding(0, 0, 0, 0)
                        val lp = mContent.layoutParams
                        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
                        mContent.layoutParams = lp
                    }
                }

                if (columns > 3) {
                    val mBackgroundView =
                        it.thisObject.getObjectFieldAs<GridView>("mBackgroundView")
                    mBackgroundView.setPadding(
                        mBackgroundView.paddingLeft / 3,
                        mBackgroundView.paddingTop,
                        mBackgroundView.paddingRight / 3,
                        mBackgroundView.paddingBottom
                    )
                }
            }
        }
    }
}