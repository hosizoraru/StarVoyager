package star.sky.voyager.utils.voyager

import android.annotation.SuppressLint
import cn.fkj233.ui.activity.MIUIActivity.Companion.context

object Resource {
    @SuppressLint("DiscouragedApi")
    fun getResourceId(
        name: String,
        defResId: Int
    ): Int {
        val id = context.resources.getIdentifier(
            name,
            "string",
            context.packageName
        )
        return if (id != 0) id else defResId
    }
}