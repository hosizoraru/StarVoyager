package star.sky.voyager.hook.hooks.home

import android.view.ViewGroup
import android.widget.GridView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import de.robv.android.xposed.XposedHelpers
import star.sky.voyager.utils.api.hookAfterAllMethods
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable

object FolderColumnsCount : HookRegister() {
    override fun init() {
        val value = getInt("home_folder_columns", 3)
        if (value == 3) return
        loadClass("com.miui.home.launcher.Folder").hookAfterAllMethods(
            "bind"
        ) {
            val columns: Int = value
            val mContent = XposedHelpers.getObjectField(it.thisObject, "mContent") as GridView
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
                    XposedHelpers.getObjectField(it.thisObject, "mBackgroundView") as ViewGroup
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