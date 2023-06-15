package star.sky.voyager.utils.yife

import android.annotation.SuppressLint
import cn.fkj233.ui.activity.MIUIActivity

object Resource {
    @SuppressLint("DiscouragedApi")
    fun getResourceId(
        name: String,
        defResId: Int
    ): Int {
        val id = MIUIActivity.context.resources.getIdentifier(
            name,
            "string",
            MIUIActivity.context.packageName
        )
        return if (id != 0) id else defResId
    }
}