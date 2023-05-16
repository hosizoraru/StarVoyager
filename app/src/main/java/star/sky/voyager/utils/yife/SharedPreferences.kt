package star.sky.voyager.utils.yife

object SharedPreferences {
    fun android.content.SharedPreferences.putStringSet(
        key: String,
        value: Set<String>
    ) {
        val editor = edit()
        editor.putStringSet(key, value)
        editor.apply()
    }

}