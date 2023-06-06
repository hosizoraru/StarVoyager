package star.sky.voyager.tiles

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.service.quicksettings.Tile.STATE_ACTIVE
import android.service.quicksettings.Tile.STATE_INACTIVE
import android.service.quicksettings.TileService
import star.sky.voyager.utils.yife.Terminal.exec

class HighBrightnessMode : TileService() {
    private var isActive = false
    private var originalBrightness: String? = null
    private val screenStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_SCREEN_OFF) {
                qsTile.also {
                    it.state = STATE_INACTIVE
                    it.updateTile()
                }
                isActive = false
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter(Intent.ACTION_SCREEN_OFF)
        registerReceiver(screenStateReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(screenStateReceiver)
    }

    override fun onClick() {
        super.onClick()
        isActive = !isActive
        qsTile.also {
            it.state = if (isActive) {
                originalBrightness =
                    exec("cat /sys/class/mi_display/disp-DSI-0/brightness_clone").trim()
                val maxBrightness =
                    exec("cat /sys/class/mi_display/disp-DSI-0/max_brightness_clone").trim()
                exec("echo $maxBrightness > /sys/class/mi_display/disp-DSI-0/brightness_clone")
                STATE_ACTIVE
            } else {
                originalBrightness?.let { exec("echo $it > /sys/class/mi_display/disp-DSI-0/brightness_clone") }
                STATE_INACTIVE
            }
            it.updateTile()
        }
    }

    override fun onStartListening() {
        super.onStartListening()
        qsTile.also {
            it.state = if (isActive) STATE_ACTIVE else STATE_INACTIVE
            it.updateTile()
        }
    }
}
