package star.sky.voyager.tiles

import android.service.quicksettings.Tile.STATE_ACTIVE
import android.service.quicksettings.Tile.STATE_INACTIVE
import android.service.quicksettings.TileService
import star.sky.voyager.utils.yife.Terminal.exec

class DesktopMode : TileService() {
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
                exec("settings put system miui_dkt_mode 1")
                STATE_ACTIVE
            } else {
                exec("settings put system miui_dkt_mode 0")
                STATE_INACTIVE
            }
            it.updateTile()
        }
    }
//    private var isActive = true
//
//    override fun onStartListening() {
//        super.onStartListening()
//        qsTile.state = STATE_ACTIVE
//        qsTile.updateTile()
//    }
//
//    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
//    override fun onClick() {
//        super.onClick()
//        val intent = Intent()
//        val comp = ComponentName(
//            "com.miui.freeform",
//            "com.miui.freeform.guide.MiuiDesktopModeGuideActivity"
//        )
//        intent.component = comp
//        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
//        startActivityAndCollapse(intent)
//    }
}