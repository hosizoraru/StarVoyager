package star.sky.voyager.tiles

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.service.quicksettings.Tile.STATE_ACTIVE
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi


class CirculateWorld : TileService() {
    private var isActive = true

    override fun onStartListening() {
        super.onStartListening()
        qsTile.state = STATE_ACTIVE
        qsTile.updateTile()
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onClick() {
        super.onClick()
        val intent = Intent()
        val comp = ComponentName(
            "com.milink.service",
            "com.miui.circulate.world.CirculateWorldActivity"
        )
        intent.component = comp
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivityAndCollapse(intent)
    }
}
