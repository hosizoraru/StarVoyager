package star.sky.voyager.utils.voyager

import cn.fkj233.ui.activity.MIUIActivity.Companion.context
import star.sky.voyager.utils.voyager.Resource.getResourceId

object SafeSharedPreferences {
    fun cn.fkj233.ui.activity.data.SafeSharedPreferences.getResourceString(
        key: String,
        defResId: Int
    ): String {
        return if (mSP == null) {
            context.getString(defResId)
        } else {
            val storedName = mSP!!.getString(key, null)
            if (storedName != null) {
                val id = getResourceId(storedName, defResId)
                context.getString(id)
            } else {
                context.getString(defResId)
            }
        }
    }
}