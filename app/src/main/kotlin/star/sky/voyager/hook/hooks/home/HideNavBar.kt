package star.sky.voyager.hook.hooks.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.provider.Settings
import android.util.AttributeSet
import android.view.MotionEvent
import com.github.kyuubiran.ezxhelper.ClassUtils.getStaticObjectOrNull
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNull
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethod
import com.github.kyuubiran.ezxhelper.ObjectUtils.setObject
import com.github.kyuubiran.ezxhelper.finders.ConstructorFinder.`-Static`.constructorFinder
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister


@SuppressLint("StaticFieldLeak")
object HideNavBar : HookRegister() {
    private var mHideGestureLine = false
    private var sIsNeedInjectMotionEvent = false
    private var hasSIsNeedInjectMotionEvent = true
    private lateinit var motionEvent: MotionEvent
    private var mTopMargin = 0
    private var mCurrentTopMargin = 0
    private var isLandScapeActually = false
    private lateinit var context: Context
    override fun init() {
        val statusBarControllerCls =
            loadClass("com.miui.launcher.utils.StatusBarController")
        val navStubViewCls =
            loadClass("com.miui.home.recents.NavStubView")
        val antiMistakeTouchViewCls =
            loadClass("com.miui.home.recents.AntiMistakeTouchView")

        val DISABLE_EXPAND =
            getStaticObjectOrNull(statusBarControllerCls, "DISABLE_EXPAND") as Int

        navStubViewCls.methodFinder()
            .filterByName("injectMotionEvent")
            .filterByParamTypes(Int::class.javaPrimitiveType)
            .first().createHook {
                before { param ->
                    if (hasSIsNeedInjectMotionEvent) try {
                        //旧版系统桌面用sIsNeedInjectMotionEvent判断是否点击
                        sIsNeedInjectMotionEvent =
                            getObjectOrNull(
                                param.thisObject,
                                "sIsNeedInjectMotionEvent"
                            ) as Boolean
                    } catch (throwable: Throwable) {
                        hasSIsNeedInjectMotionEvent = false
//                        val packageInfo = context.packageManager.getPackageInfo(
//                            context.packageName, 0
//                        )
                    }

                    //新版系统桌面用mHideGestureLine判断是否点击
                    mHideGestureLine =
                        getObjectOrNull(param.thisObject, "mHideGestureLine") as Boolean
                    motionEvent =
                        getObjectOrNull(param.thisObject, "mDownEvent") as MotionEvent
                    if (motionEvent.flags and DISABLE_EXPAND == 0) {
                        setObject(param.thisObject, "mHideGestureLine", false)
                        if (hasSIsNeedInjectMotionEvent) setObject(
                            param.thisObject,
                            "sIsNeedInjectMotionEvent",
                            true
                        )
                    }
                }

                after { param ->
                    if (motionEvent.flags and DISABLE_EXPAND == 0) {
                        setObject(
                            param.thisObject,
                            "mHideGestureLine",
                            mHideGestureLine
                        )
                        if (hasSIsNeedInjectMotionEvent) setObject(
                            param.thisObject,
                            "sIsNeedInjectMotionEvent",
                            sIsNeedInjectMotionEvent
                        )
                    }
                }
            }

        //获取初始横竖屏状态
        navStubViewCls.constructorFinder()
            .filterByParamTypes(Context::class.java)
            .toList().createHooks {
                after { param ->
                    context = param.args[0] as Context
                    isLandScapeActually =
                        invokeMethod(param.thisObject, "isLandScapeActually") as Boolean
                }
            }

        //旋转屏幕时更新横竖屏状态
        navStubViewCls.methodFinder()
            .filterByName("onConfigurationChanged")
            .filterByParamTypes(Configuration::class.java)
            .first().createHook {
                after { param ->
                    isLandScapeActually =
                        invokeMethod(param.thisObject, "isLandScapeActually") as Boolean
                }
            }

        //二次滑动确认时上滑显示的view,显示时再hook
        antiMistakeTouchViewCls.constructorFinder()
            .filterByParamTypes(
                Context::class.java,
                AttributeSet::class.java,
                Int::class.javaPrimitiveType
            )
            .toList().createHooks {
                after { param ->
                    mTopMargin =
                        getObjectOrNull(param.thisObject, "mTopMargin") as Int
                    mCurrentTopMargin = mTopMargin
                }
            }

        antiMistakeTouchViewCls.methodFinder()
            .filterByName("slideUp")
            .first().createHook {
                after { param ->
                    mCurrentTopMargin = 0
                }
            }

        antiMistakeTouchViewCls.methodFinder()
            .filterByName("slideDown")
            .first().createHook {
                after { param ->
                    mCurrentTopMargin = mTopMargin
                }
            }

        //滑动事件
        navStubViewCls.methodFinder()
            .filterByName("onTouchEvent")
            .filterByParamTypes(MotionEvent::class.java)
            .first().createHook {
                before { param ->
                    mHideGestureLine =
                        getObjectOrNull(param.thisObject, "mHideGestureLine") as Boolean
                    //横屏
                    if (isLandScapeActually) {
                        //需要二次滑动确认
                        if (Settings.Global.getInt(
                                context.contentResolver,
                                "show_mistake_touch_toast",
                                1
                            ) === 1
                        ) {
                            //二次滑动确认时上滑的view已经显示出来
                            if (mCurrentTopMargin == 0) {
                                setObject(
                                    param.thisObject,
                                    "mHideGestureLine",
                                    false
                                )
                            }
                        } else {
                            setObject(
                                param.thisObject,
                                "mHideGestureLine",
                                false
                            )
                        }
                    } else {
                        //二次滑动确认打开了，但是不像横屏一样显示一个view以及再划一次，是我机子的问题还是本身逻辑如此？
                        setObject(param.thisObject, "mHideGestureLine", false)
                    }
                }

                after { param ->
                    setObject(
                        param.thisObject,
                        "mHideGestureLine",
                        mHideGestureLine
                    )
                }
            }

        navStubViewCls.methodFinder()
            .filterByName("getWindowParam")
            .filterByParamTypes(Int::class.javaPrimitiveType)
            .first().createHook {
                before { param ->
                    mHideGestureLine =
                        getObjectOrNull(param.thisObject, "mHideGestureLine") as Boolean
                    setObject(param.thisObject, "mHideGestureLine", false)
                }

                after { param ->
                    setObject(
                        param.thisObject,
                        "mHideGestureLine",
                        mHideGestureLine
                    )
                }
            }
    }
}