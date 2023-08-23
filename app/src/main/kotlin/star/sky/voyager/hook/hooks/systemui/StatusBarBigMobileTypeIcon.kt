package star.sky.voyager.hook.hooks.systemui

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.api.callMethod
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.api.setObjectField
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getBoolean
import star.sky.voyager.utils.key.XSPUtils.getFloat
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable

object StatusBarBigMobileTypeIcon : HookRegister() {

    private val getLocation by lazy {
        getInt("big_mobile_type_location", 1)
    }
    private val upAndDownPosition by lazy {
        getInt("big_mobile_type_icon_up_and_down_position", 0)
    }
    private val leftAndRightMargin by lazy {
        getInt("big_mobile_type_icon_left_and_right_margins", 0)
    }
    private val isBold by lazy {
        getBoolean("big_mobile_type_icon_bold", true)
    }
    private val size by lazy {
        getFloat("big_mobile_type_icon_size", 12.5f)
    }
    private val isOnlyShowNetwork by lazy {
        getBoolean("big_mobile_type_only_show_network_card", false)
    }

    override fun init() = hasEnable("big_mobile_type_icon") {
        //使网络类型单独显示
        val statusBarMobileViewClass =
            loadClass("com.android.systemui.statusbar.StatusBarMobileView")
        statusBarMobileViewClass.methodFinder()
            .filterByName("applyMobileState")
            .first().createHook {
                before {
                    val mobileIconState = it.args[0]
                    mobileIconState.setObjectField("showMobileDataTypeSingle", true)
                }
            }

        statusBarMobileViewClass.methodFinder()
            .filterByName("initViewState")
            .first().createHook {
                after {
                    val mobileIconState = it.args[0]
                    val statusBarMobileView = it.thisObject as ViewGroup
                    val context: Context = statusBarMobileView.context
                    val res: Resources = context.resources

                    //获取组件
                    val mobileContainerLeftId: Int =
                        res.getIdentifier("mobile_container_left", "id", "com.android.systemui")
                    val mobileContainerLeft =
                        statusBarMobileView.findViewById<ViewGroup>(mobileContainerLeftId)

                    val mobileGroupId: Int =
                        res.getIdentifier("mobile_group", "id", "com.android.systemui")
                    val mobileGroup = statusBarMobileView.findViewById<ViewGroup>(mobileGroupId)

                    val mobileTypeSingleId: Int =
                        res.getIdentifier("mobile_type_single", "id", "com.android.systemui")
                    val mobileTypeSingle =
                        statusBarMobileView.findViewById<TextView>(mobileTypeSingleId)

                    //更改顺序
                    if (getLocation == 1) {
                        mobileGroup.removeView(mobileTypeSingle)
                        mobileGroup.addView(mobileTypeSingle)
                        mobileGroup.removeView(mobileContainerLeft)
                        mobileGroup.addView(mobileContainerLeft)
                    }

                    //更改样式
                    mobileTypeSingle.textSize = size
                    if (isBold) {
                        mobileTypeSingle.typeface = Typeface.DEFAULT_BOLD
                    }
                    mobileTypeSingle.setPadding(
                        leftAndRightMargin, upAndDownPosition, leftAndRightMargin, 0
                    )

                    it.thisObject.callMethod("updateState", mobileIconState)
                }
            }
        //显示非上网卡的大图标
        if (!isOnlyShowNetwork) {
            statusBarMobileViewClass.methodFinder()
                .filterByName("updateState")
                .first().createHook {
                    after {
                        val mobileIconState = it.args[0]
                        val statusBarMobileView = it.thisObject as ViewGroup
                        val context: Context = statusBarMobileView.context
                        val res: Resources = context.resources

                        val mobileTypeSingleId: Int =
                            res.getIdentifier("mobile_type_single", "id", "com.android.systemui")
                        val mobileTypeSingle =
                            statusBarMobileView.findViewById<TextView>(mobileTypeSingleId)

                        if (!mobileIconState.getObjectFieldAs<Boolean>("dataConnected") && !mobileIconState.getObjectFieldAs<Boolean>(
                                "wifiAvailable"
                            )
                        ) {
                            mobileTypeSingle.visibility = View.VISIBLE
                        }
                    }
                }
        }
    }
}