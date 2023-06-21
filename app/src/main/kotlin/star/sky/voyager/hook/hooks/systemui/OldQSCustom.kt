package star.sky.voyager.hook.hooks.systemui

import android.content.res.Configuration
import android.view.ViewGroup
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.setObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable

object OldQSCustom : HookRegister() {
    override fun init() = hasEnable("old_qs_custom_switch") {
        val mRows = getInt("qs_custom_rows", 3)
        val mRowsHorizontal = getInt("qs_custom_rows_horizontal", 2)
        val mColumns = getInt("qs_custom_columns", 4)
        val mColumnsUnexpanded = getInt("qs_custom_columns_unexpanded", 5)

        loadClass("com.android.systemui.qs.MiuiQuickQSPanel").methodFinder()
            .filterByName("setMaxTiles")
            .filterByParamCount(1)
            .first().createHook {
                before {
                    //未展开时的列数
                    it.args[0] = mColumnsUnexpanded
                }
            }

        loadClass("com.android.systemui.qs.MiuiTileLayout").methodFinder()
            .filterByName("updateColumns")
            .first().createHook {
                after {
                    //展开时的列数
                    it.thisObject.setObjectField("mColumns", mColumns)
                }
            }

        loadClass("com.android.systemui.qs.MiuiTileLayout").methodFinder()
            .filterByName("updateResources")
            .first().createHook {
                after {
                    //展开时的行数
                    val viewGroup = it.thisObject as ViewGroup
                    val mConfiguration: Configuration = viewGroup.context.resources.configuration
                    if (mConfiguration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        viewGroup.setObjectField("mMaxAllowedRows", mRows)
                    } else {
                        viewGroup.setObjectField("mMaxAllowedRows", mRowsHorizontal)
                    }
                    viewGroup.requestLayout()
                }
            }
    }
}