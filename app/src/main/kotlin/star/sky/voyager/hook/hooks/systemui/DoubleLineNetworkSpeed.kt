package star.sky.voyager.hook.hooks.systemui

import android.content.Context
import android.net.TrafficStats
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.moduleRes
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.ConstructorFinder.`-Static`.constructorFinder
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.R
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getBoolean
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable
import java.text.DecimalFormat

object DoubleLineNetworkSpeed : HookRegister() {

    private var mLastTotalUp: Long = 0
    private var mLastTotalDown: Long = 0

    private var lastTimeStampTotalUp: Long = 0
    private var lastTimeStampTotalDown: Long = 0

    private var upIcon = ""
    private var downIcon = ""

    private val getDualSize = getInt("status_bar_network_speed_dual_row_size", 0)
    private val getDualAlign = getInt("status_bar_network_speed_dual_row_gravity", 0)

    override fun init() = hasEnable("status_bar_dual_row_network_speed") {

        getUpIcon()
        getDownIcon()

        loadClass("com.android.systemui.statusbar.views.NetworkSpeedView").constructorFinder()
            .first {
                parameterCount == 2
            }.createHook {
                after {
                    val mView = it.thisObject as TextView
                    mView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 7f)
                    if (getDualSize != 0) {
                        mView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, getDualSize.toFloat())
                    }
                    mView.isSingleLine = false
                    mView.setLineSpacing(0F, 0.8F)
                    if (getDualAlign == 0) {
                        mView.gravity = Gravity.START or Gravity.CENTER_VERTICAL
                    } else {
                        mView.gravity = Gravity.END or Gravity.CENTER_VERTICAL
                    }
                }
            }

        loadClass("com.android.systemui.statusbar.policy.NetworkSpeedController").methodFinder()
            .first {
                name == "formatSpeed" && parameterCount == 2
            }.createHook {
                before {
                    if (getDualAlign == 0) {
                        it.result = "$upIcon ${getTotalUpSpeed(it.args[0] as Context)}\n$downIcon ${
                            getTotalDownloadSpeed(it.args[0] as Context)
                        }"
                    } else {
                        it.result = "${getTotalUpSpeed(it.args[0] as Context)} $upIcon\n${
                            getTotalDownloadSpeed(it.args[0] as Context)
                        } $downIcon"
                    }
                }
            }
    }

    //获取总的上行速度
    private fun getTotalUpSpeed(context: Context): String {
        val totalUpSpeed: Float

        val currentTotalTxBytes = TrafficStats.getTotalTxBytes()
        val nowTimeStampTotalUp = System.currentTimeMillis()

        //计算上传速度
        val bytes =
            (currentTotalTxBytes - mLastTotalUp) * 1000 / (nowTimeStampTotalUp - lastTimeStampTotalUp).toFloat()
        val unit: String

        if (bytes >= 1048576) {
            totalUpSpeed = DecimalFormat("0.0").format(bytes / 1048576).toFloat()
            unit = context.resources.getString(
                context.resources.getIdentifier(
                    "megabyte_per_second",
                    "string",
                    context.packageName
                )
            )
        } else {
            totalUpSpeed = DecimalFormat("0.0").format(bytes / 1024).toFloat()
            unit = context.resources.getString(
                context.resources.getIdentifier(
                    "kilobyte_per_second",
                    "string",
                    context.packageName
                )
            )
        }

        //保存当前的流量总和和上次的时间戳
        mLastTotalUp = currentTotalTxBytes
        lastTimeStampTotalUp = nowTimeStampTotalUp

        // 隐藏慢速
        val hideLow = getBoolean("hide_slow_speed_network_speed", false)

        // 慢速水平
        val lowLevel = getInt("slow_speed_degree", 1) * 512.toFloat()

        if (!hideLow || bytes >= lowLevel) {
            getUpIcon()
        }

        return if (hideLow && bytes < lowLevel) {
            upIcon = ""
            ""
        } else {
            if (totalUpSpeed >= 100) {
                "${totalUpSpeed.toInt()}$unit"
            } else {
                "${totalUpSpeed}$unit"
            }
        }
    }

    //获取总的下行速度
    private fun getTotalDownloadSpeed(context: Context): String {
        val totalDownSpeed: Float
        val currentTotalRxBytes = TrafficStats.getTotalRxBytes()
        val nowTimeStampTotalDown = System.currentTimeMillis()

        //计算下行速度
        val bytes =
            (currentTotalRxBytes - mLastTotalDown) * 1000 / (nowTimeStampTotalDown - lastTimeStampTotalDown).toFloat()

        val unit: String

        if (bytes >= 1048576) {
            totalDownSpeed = DecimalFormat("0.0").format(bytes / 1048576).toFloat()
            unit = context.resources.getString(
                context.resources.getIdentifier(
                    "megabyte_per_second",
                    "string",
                    context.packageName
                )
            )
        } else {
            totalDownSpeed = DecimalFormat("0.0").format(bytes / 1024).toFloat()
            unit = context.resources.getString(
                context.resources.getIdentifier(
                    "kilobyte_per_second",
                    "string",
                    context.packageName
                )
            )
        }
        //保存当前的流量总和和上次的时间戳
        mLastTotalDown = currentTotalRxBytes
        lastTimeStampTotalDown = nowTimeStampTotalDown

        // 隐藏慢速
        val hideLow = getBoolean("hide_slow_speed_network_speed", false)

        // 慢速水平
        val lowLevel = getInt("slow_speed_degree", 1) * 512.toFloat()

        if (!hideLow || bytes >= lowLevel) {
            getDownIcon()
        }

        return if (hideLow && bytes < lowLevel) {
            downIcon = ""
            ""
        } else {
            if (totalDownSpeed >= 100) {
                "${totalDownSpeed.toInt()}$unit"
            } else {
                "${totalDownSpeed}$unit"
            }
        }
    }

    private fun getUpIcon() {
        val none = moduleRes.getString(R.string.none)

        if (getString("status_bar_network_speed_dual_row_icon", none) != none) {
            upIcon =
                getString("status_bar_network_speed_dual_row_icon", none)?.firstOrNull()
                    .toString()
        }
    }

    private fun getDownIcon() {
        val none = moduleRes.getString(R.string.none)

        if (getString("status_bar_network_speed_dual_row_icon", none) != none) {
            downIcon =
                getString("status_bar_network_speed_dual_row_icon", none)?.lastOrNull()
                    .toString()
        }
    }
}