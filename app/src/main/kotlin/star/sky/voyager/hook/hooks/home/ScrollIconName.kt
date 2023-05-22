package star.sky.voyager.hook.hooks.home

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.classLoader
import com.github.kyuubiran.ezxhelper.Log
import star.sky.voyager.utils.api.callMethod
import star.sky.voyager.utils.api.getObjectField
import star.sky.voyager.utils.api.hookAfterMethod
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable

object ScrollIconName : HookRegister() {
    @SuppressLint("DiscouragedApi")
    override fun init() = hasEnable("home_scroll_icon_name") {
        val launcherClass = loadClass("com.miui.home.launcher.Launcher")
        val shortcutInfoClass = loadClass("com.miui.home.launcher.ShortcutInfo")

        try {
            "com.miui.home.launcher.ItemIcon".hookAfterMethod(
                classLoader,
                "onFinishInflate"
            ) {
                val mTitle = it.thisObject.getObjectField("mTitle") as TextView
                mTitleScrolling(mTitle)
            }
            "com.miui.home.launcher.maml.MaMlWidgetView".hookAfterMethod(
                classLoader,
                "onFinishInflate"
            ) {
                val mTitle = it.thisObject.getObjectField("mTitleTextView") as TextView
                mTitleScrolling(mTitle)
            }
            "com.miui.home.launcher.LauncherMtzGadgetView".hookAfterMethod(
                classLoader,
                "onFinishInflate"
            ) {
                val mTitle = it.thisObject.getObjectField("mTitleTextView") as TextView
                mTitleScrolling(mTitle)
            }
            "com.miui.home.launcher.LauncherWidgetView".hookAfterMethod(
                classLoader,
                "onFinishInflate"
            ) {
                val mTitle = it.thisObject.getObjectField("mTitleTextView") as TextView
                mTitleScrolling(mTitle)
            }
            "com.miui.home.launcher.ShortcutIcon".hookAfterMethod(
                classLoader,
                "fromXml",
                Int::class.javaPrimitiveType,
                launcherClass,
                ViewGroup::class.java,
                shortcutInfoClass
            ) {
                val buddyIconView = it.args[3].callMethod("getBuddyIconView", it.args[2]) as View
                val mTitle = buddyIconView.getObjectField("mTitle") as TextView
                mTitleScrolling(mTitle)
            }
            "com.miui.home.launcher.ShortcutIcon".hookAfterMethod(
                classLoader,
                "createShortcutIcon",
                Int::class.javaPrimitiveType,
                launcherClass,
                ViewGroup::class.java
            ) {
                val buddyIcon = it.result as View
                val mTitle = buddyIcon.getObjectField("mTitle") as TextView
                mTitleScrolling(mTitle)
            }
            "com.miui.home.launcher.common.Utilities".hookAfterMethod(
                classLoader,
                "adaptTitleStyleToWallpaper",
                Context::class.java,
                TextView::class.java,
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType
            ) {
                val mTitle = it.args[1] as TextView
                if (mTitle.id == mTitle.resources.getIdentifier(
                        "icon_title",
                        "id",
                        "com.miui.home"
                    )
                ) {
                    mTitleScrolling(mTitle)
                }
            }
        } catch (e: Throwable) {
            Log.ex(e)
        }
    }

    private fun mTitleScrolling(mTitle: TextView) {
        mTitle.ellipsize = TextUtils.TruncateAt.MARQUEE
        mTitle.isHorizontalFadingEdgeEnabled = true
        mTitle.setSingleLine()
        mTitle.marqueeRepeatLimit = -1
        mTitle.isSelected = true
        mTitle.setHorizontallyScrolling(true)
    }
}