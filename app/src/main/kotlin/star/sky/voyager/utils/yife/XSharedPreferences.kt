package star.sky.voyager.utils.yife


import de.robv.android.xposed.XSharedPreferences
import star.sky.voyager.BuildConfig.APPLICATION_ID

object XSharedPreferences {
    const val prefFileName = "voyager_config"
    private val prefs = XSharedPreferences(APPLICATION_ID, prefFileName)
    fun getBoolean(key: String, defValue: Boolean): Boolean {

        if (prefs.hasFileChanged()) {
            prefs.reload()
        }
        return prefs.getBoolean(key, defValue)
    }

    fun getString(key: String, defValue: String): String {
        if (prefs.hasFileChanged()) {
            prefs.reload()
        }
        return prefs.getString(key, defValue) ?: defValue
    }

    fun getStringSet(key: String, defValue: MutableSet<String>): MutableSet<String> {
        if (prefs.hasFileChanged()) {
            prefs.reload()
        }
        return prefs.getStringSet(key, defValue) ?: defValue
    }

}