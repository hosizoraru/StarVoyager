package star.sky.voyager.tiles

import android.service.quicksettings.Tile.STATE_ACTIVE
import android.service.quicksettings.Tile.STATE_INACTIVE
import android.service.quicksettings.TileService
import star.sky.voyager.utils.yife.Terminal.exec

class CameraSensor : TileService() {
    private var isActive = true

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
                exec("service call sensor_privacy 10 i32 0 i32 3 i32 2 i32 0")
                STATE_ACTIVE
            } else {
                exec("service call sensor_privacy 10 i32 0 i32 3 i32 2 i32 1")
                STATE_INACTIVE
            }
            it.updateTile()
        }
    }
}
