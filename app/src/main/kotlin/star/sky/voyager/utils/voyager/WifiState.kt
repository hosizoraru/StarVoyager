package star.sky.voyager.utils.voyager

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import com.github.kyuubiran.ezxhelper.EzXHelper.appContext

object WifiState {
    /**
     * 获取并返回Wifi的开启状态
     * @return Wifi的开启状态
     * @author Voyager
     */
    fun isWifiEnabled(): Boolean {
        val wifiManager =
            appContext.applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager
        return wifiManager?.isWifiEnabled ?: false
    }

    /**
     * 获取并返回Wifi的链接状态
     * @return Wifi的链接状态
     * @author Voyager
     */
    fun isWifiConnected(): Boolean {
        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val network = connectivityManager?.activeNetwork
        val networkCapabilities = connectivityManager?.getNetworkCapabilities(network)
        return networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false
    }
}