package star.sky.voyager.hook.hooks.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.provider.Settings
import android.util.AttributeSet
import android.view.MotionEvent
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.XposedHelpers.findAndHookConstructor
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import star.sky.voyager.utils.init.HookRegister


@SuppressLint("StaticFieldLeak")
object HideNavBar : HookRegister() {
    var DISABLE_EXPAND = -1
    private var mHideGestureLine = false
    private var sIsNeedInjectMotionEvent = false
    private var hasSIsNeedInjectMotionEvent = true
    private lateinit var motionEvent: MotionEvent
    private var mTopMargin = 0
    private var mCurrentTopMargin = 0
    private var isLandScapeActually = false
    private lateinit var context: Context
    override fun init() {
        if (DISABLE_EXPAND == -1) {
            val clazz =
                loadClass("com.miui.launcher.utils.StatusBarController")
            DISABLE_EXPAND = XposedHelpers.getStaticIntField(clazz, "DISABLE_EXPAND")
        }

        findAndHookMethod("com.miui.home.recents.NavStubView",
            classLoader,
            "injectMotionEvent",
            Int::class.javaPrimitiveType,
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    super.beforeHookedMethod(param)
                    if (hasSIsNeedInjectMotionEvent) try {
                        //旧版系统桌面用sIsNeedInjectMotionEvent判断是否点击
                        sIsNeedInjectMotionEvent = XposedHelpers.getBooleanField(
                            param.thisObject,
                            "sIsNeedInjectMotionEvent"
                        )
                    } catch (throwable: Throwable) {
                        hasSIsNeedInjectMotionEvent = false
                        val packageInfo = context.packageManager.getPackageInfo(
                            context.packageName, 0
                        )
                        XposedBridge.log("MIUI隐藏小白条/MIUI HideNavBar:没有sIsNeedInjectMotionEvent，当前系统桌面版本——" + packageInfo.versionName)
                    }

                    //新版系统桌面用mHideGestureLine判断是否点击
                    mHideGestureLine =
                        XposedHelpers.getBooleanField(param.thisObject, "mHideGestureLine")
                    motionEvent =
                        XposedHelpers.getObjectField(param.thisObject, "mDownEvent") as MotionEvent
                    if (motionEvent.flags and DISABLE_EXPAND == 0) {
                        XposedHelpers.setBooleanField(param.thisObject, "mHideGestureLine", false)
                        if (hasSIsNeedInjectMotionEvent) XposedHelpers.setBooleanField(
                            param.thisObject,
                            "sIsNeedInjectMotionEvent",
                            true
                        )
                    }
                }

                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    super.afterHookedMethod(param)
                    if (motionEvent.flags and DISABLE_EXPAND == 0) {
                        XposedHelpers.setBooleanField(
                            param.thisObject,
                            "mHideGestureLine",
                            mHideGestureLine
                        )
                        if (hasSIsNeedInjectMotionEvent) XposedHelpers.setBooleanField(
                            param.thisObject,
                            "sIsNeedInjectMotionEvent",
                            sIsNeedInjectMotionEvent
                        )
                    }
                }
            })

        //获取初始横竖屏状态
        findAndHookConstructor("com.miui.home.recents.NavStubView",
            classLoader,
            Context::class.java,
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    super.afterHookedMethod(param)
                    context = param.args[0] as Context
                    isLandScapeActually =
                        XposedHelpers.callMethod(param.thisObject, "isLandScapeActually") as Boolean
                }
            })


        //旋转屏幕时更新横竖屏状态
        findAndHookMethod("com.miui.home.recents.NavStubView",
            classLoader,
            "onConfigurationChanged",
            Configuration::class.java,
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    super.afterHookedMethod(param)
                    isLandScapeActually =
                        XposedHelpers.callMethod(param.thisObject, "isLandScapeActually") as Boolean
                }
            })

        //二次滑动确认时上滑显示的view,显示时再hook

        //二次滑动确认时上滑显示的view,显示时再hook
        findAndHookConstructor("com.miui.home.recents.AntiMistakeTouchView",
            classLoader,
            Context::class.java,
            AttributeSet::class.java,
            Int::class.javaPrimitiveType,
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    super.afterHookedMethod(param)
                    mTopMargin = XposedHelpers.getIntField(param.thisObject, "mTopMargin")
                    mCurrentTopMargin = mTopMargin
                }
            })

        findAndHookMethod("com.miui.home.recents.AntiMistakeTouchView",
            classLoader,
            "slideUp",
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    super.afterHookedMethod(param)
                    mCurrentTopMargin = 0
                }
            })
        findAndHookMethod("com.miui.home.recents.AntiMistakeTouchView",
            classLoader,
            "slideDown",
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    super.afterHookedMethod(param)
                    mCurrentTopMargin = mTopMargin
                }
            })

        //滑动事件
        findAndHookMethod("com.miui.home.recents.NavStubView",
            classLoader,
            "onTouchEvent",
            MotionEvent::class.java,
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    super.beforeHookedMethod(param)
                    mHideGestureLine =
                        XposedHelpers.getBooleanField(param.thisObject, "mHideGestureLine")
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
                                XposedHelpers.setBooleanField(
                                    param.thisObject,
                                    "mHideGestureLine",
                                    false
                                )
                            }
                        } else {
                            XposedHelpers.setBooleanField(
                                param.thisObject,
                                "mHideGestureLine",
                                false
                            )
                        }
                    } else {
                        //二次滑动确认打开了，但是不像横屏一样显示一个view以及再划一次，是我机子的问题还是本身逻辑如此？
                        XposedHelpers.setBooleanField(param.thisObject, "mHideGestureLine", false)
                    }
                }

                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    super.afterHookedMethod(param)
                    XposedHelpers.setBooleanField(
                        param.thisObject,
                        "mHideGestureLine",
                        mHideGestureLine
                    )
                }
            })

        findAndHookMethod("com.miui.home.recents.NavStubView",
            classLoader,
            "getWindowParam",
            Int::class.javaPrimitiveType,
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    super.beforeHookedMethod(param)
                    mHideGestureLine =
                        XposedHelpers.getBooleanField(param.thisObject, "mHideGestureLine")
                    XposedHelpers.setBooleanField(param.thisObject, "mHideGestureLine", false)
                }

                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    super.afterHookedMethod(param)
                    XposedHelpers.setBooleanField(
                        param.thisObject,
                        "mHideGestureLine",
                        mHideGestureLine
                    )
                }
            })
    }
}