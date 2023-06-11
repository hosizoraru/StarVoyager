package star.sky.voyager.tiles

import android.service.quicksettings.Tile.STATE_ACTIVE
import android.service.quicksettings.Tile.STATE_INACTIVE
import android.service.quicksettings.TileService
import star.sky.voyager.utils.yife.XSharedPreferences.prefFileName

class AllowScreenshots : TileService() {
    private val key = "disable_flag_secure"

    override fun onClick() {
        super.onClick()
        try {
            val pref = getSharedPreferences(prefFileName, MODE_WORLD_READABLE)
            val prefEditor = pref.edit()
            if (pref.getBoolean(key, false)) {
                prefEditor.putBoolean(key, false)
                qsTile.state = STATE_INACTIVE
            } else {
                prefEditor.putBoolean(key, true)
                qsTile.state = STATE_ACTIVE
            }
            prefEditor.apply()
            qsTile.updateTile()
        } catch (_: SecurityException) {
        }
    }

    override fun onStartListening() {
        super.onStartListening()
        try {
            val pref = getSharedPreferences(prefFileName, MODE_WORLD_READABLE)
            if (pref.getBoolean(key, false)) {
                qsTile.state = STATE_ACTIVE
            } else {
                qsTile.state = STATE_INACTIVE
            }
            qsTile.updateTile()
        } catch (_: SecurityException) {
        }
    }
}