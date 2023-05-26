package star.sky.voyager.hook.hooks.home

import android.content.Context
import android.content.Intent
import android.view.MotionEvent
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XposedHelpers.getAdditionalInstanceField
import de.robv.android.xposed.XposedHelpers.setAdditionalInstanceField
import star.sky.voyager.utils.api.DoubleTapController
import star.sky.voyager.utils.api.callMethod
import star.sky.voyager.utils.api.callMethodAs
import star.sky.voyager.utils.api.getObjectFieldAs
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object DoubleTapToSleep : HookRegister() {
    override fun init() = hasEnable("double_tap_to_sleep") {
        loadClass("com.miui.home.launcher.Workspace").constructors.createHooks {
            after {
                var mDoubleTapControllerEx =
                    getAdditionalInstanceField(it.thisObject, "mDoubleTapControllerEx")
                if (mDoubleTapControllerEx != null) return@after
                mDoubleTapControllerEx = DoubleTapController((it.args[0] as Context))
                setAdditionalInstanceField(
                    it.thisObject,
                    "mDoubleTapControllerEx",
                    mDoubleTapControllerEx
                )
            }
        }
        loadClass("com.miui.home.launcher.Workspace").methodFinder().first {
            name == "dispatchTouchEvent" && parameterTypes[0] == MotionEvent::class.java
        }.createHook {
            before {
                val mDoubleTapControllerEx = getAdditionalInstanceField(
                    it.thisObject,
                    "mDoubleTapControllerEx"
                ) as DoubleTapController
                if (!mDoubleTapControllerEx.isDoubleTapEvent(it.args[0] as MotionEvent)) return@before
                val mCurrentScreenIndex = it.thisObject.getObjectFieldAs<Int>("mCurrentScreenIndex")
                val cellLayout =
                    it.thisObject.callMethod("getCellLayout", mCurrentScreenIndex)
                if (cellLayout != null) if (cellLayout.callMethodAs<Boolean>("lastDownOnOccupiedCell")) return@before
                if (it.thisObject.callMethod("isInNormalEditingMode") as Boolean) return@before
                val context = it.thisObject.callMethod("getContext") as Context
                context.sendBroadcast(
                    Intent("com.miui.app.ExtraStatusBarManager.action_TRIGGER_TOGGLE").putExtra(
                        "com.miui.app.ExtraStatusBarManager.extra_TOGGLE_ID",
                        10
                    )
                )
            }
        }
    }
}