package star.sky.voyager.utils.yife

import cn.fkj233.ui.activity.MIUIActivity
import star.sky.voyager.utils.yife.Resource.getResourceId

object SafeSharedPreferences {
    fun cn.fkj233.ui.activity.data.SafeSharedPreferences.getResourceString(
        key: String,
        defResId: Int
    ): String {
        return if (mSP == null) {
            MIUIActivity.context.getString(defResId)
        } else {
            val storedName = mSP!!.getString(key, null)
            if (storedName != null) {
                val id = getResourceId(storedName, defResId)
                MIUIActivity.context.getString(id)
            } else {
                MIUIActivity.context.getString(defResId)
            }
        }
    }
}