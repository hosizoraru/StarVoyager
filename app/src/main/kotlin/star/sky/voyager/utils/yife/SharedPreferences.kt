package star.sky.voyager.utils.yife

/**
 * SharedPreferences 工具
 */
object SharedPreferences {
    /**
     * 向当前 SharedPreferences 中放入一个 StringSet 属性
     * @param key 属性名称
     * @param value 属性值
     */
    fun android.content.SharedPreferences.putStringSet(
        key: String,
        value: Set<String>
    ) {
        val editor = edit()
        editor.putStringSet(key, value)
        editor.apply()
    }

}