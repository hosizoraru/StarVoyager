package star.sky.voyager.tiles

import android.service.quicksettings.Tile.STATE_ACTIVE
import android.service.quicksettings.Tile.STATE_INACTIVE
import android.service.quicksettings.TileService
import star.sky.voyager.utils.yife.Terminal.exec

class SpeedMode : TileService() {
    private var isActive = false

    override fun onStartListening() {
        super.onStartListening()
        qsTile.also {
            it.state = if (isActive) STATE_ACTIVE else STATE_INACTIVE
            it.updateTile()
        }
    }

    override fun onClick() {
        super.onClick()
        isActive = !isActive
        qsTile.also {
            it.state = if (isActive) {
                exec("settings put system speed_mode 1")
                STATE_ACTIVE
            } else {
                exec("settings put system speed_mode 0")
                STATE_INACTIVE
            }
            it.updateTile()
        }
    }
}