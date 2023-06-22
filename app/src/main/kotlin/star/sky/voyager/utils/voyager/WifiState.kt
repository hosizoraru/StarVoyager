package star.sky.voyager.utils.voyager

import android.content.Context
import android.net.wifi.WifiManager
import com.github.kyuubiran.ezxhelper.EzXHelper.appContext

/**
 * 获取并返回Wifi状态
 * @return Wifi状态
 * @author Voyager
 */
object WifiState {
    fun isWifiEnabled(): Boolean {
        val wifiManager = appContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.isWifiEnabled
    }
}