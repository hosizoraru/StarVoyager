package star.sky.voyager.hook.hooks.home

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getBoolean
import star.sky.voyager.utils.key.XSPUtils.getInt
import star.sky.voyager.utils.key.hasEnable

object ScrollIconName : HookRegister() {
    private val labelSize by lazy {
        getInt("icon_label_size", 12).toFloat()
    }
    private val labelMarquee by lazy {
        getBoolean("home_scroll_icon_name", false)
    }

    @SuppressLint("DiscouragedApi")
    override fun init() = hasEnable("home_scroll_icon_name") {
        val clazzLauncher = loadClass("com.miui.home.launcher.Launcher")
        val clazzShortcutInfo = loadClass("com.miui.home.launcher.ShortcutInfo")
        loadClass("com.miui.home.launcher.ItemIcon").methodFinder()
            .filterByName("onFinishInflate")
            .first().createHook {
                after {
                    val mTitle = it.thisObject.objectHelper()
                        .getObjectOrNullUntilSuperclassAs<TextView>("mTitle")
                    mTitle?.modify()
                }
            }
        loadClass("com.miui.home.launcher.maml.MaMlWidgetView").methodFinder()
            .filterByName("onFinishInflate").first().createHook {
                after {
                    val mTitle = it.thisObject.objectHelper()
                        .getObjectOrNullUntilSuperclassAs<TextView>("mTitleTextView")
                    mTitle?.modify()
                }
            }
        loadClass("com.miui.home.launcher.LauncherMtzGadgetView").methodFinder()
            .filterByName("onFinishInflate").first().createHook {
                after {
                    val mTitle = it.thisObject.objectHelper()
                        .getObjectOrNullUntilSuperclassAs<TextView>("mTitleTextView")
                    mTitle?.modify()
                }
            }
        loadClass("com.miui.home.launcher.LauncherWidgetView").methodFinder()
            .filterByName("onFinishInflate").first().createHook {
                after {
                    val mTitle = it.thisObject.objectHelper()
                        .getObjectOrNullUntilSuperclassAs<TextView>("mTitleTextView")
                    mTitle?.modify()
                }
            }
        loadClass("com.miui.home.launcher.ShortcutIcon").methodFinder()
            .filterByName("fromXml")
            .filterByAssignableParamTypes(
                Int::class.java,
                clazzLauncher,
                ViewGroup::class.java,
                clazzShortcutInfo
            ).first().createHook {
                after {
                    val buddyIconView = it.args[3].objectHelper()
                        .invokeMethodBestMatch("getBuddyIconView", null, it.args[2]) as View
                    val mTitle = buddyIconView.objectHelper()
                        .getObjectOrNullUntilSuperclassAs<TextView>("mTitle")
                    mTitle?.modify()
                }
            }
        loadClass("com.miui.home.launcher.common.Utilities").methodFinder()
            .filterByName("adaptTitleStyleToWallpaper").first()
            .createHook {
                after {
                    val mTitle = it.args[1] as TextView
                    if (mTitle.id == mTitle.resources.getIdentifier(
                            "icon_title",
                            "id",
                            hostPackageName
                        )
                    ) {
                        mTitle.modify()
                    }
                }
            }
    }

    private fun TextView.modify() {
        textSize = labelSize
        if (labelMarquee) {
            ellipsize = TextUtils.TruncateAt.MARQUEE
            isHorizontalFadingEdgeEnabled = true
            marqueeRepeatLimit = -1
            isSelected = true
            setSingleLine()
            setHorizontallyScrolling(true)
        }
    }
}

//private fun mTitleScrolling(mTitle: TextView) {
//    mTitle.ellipsize = TextUtils.TruncateAt.MARQUEE
//    mTitle.isHorizontalFadingEdgeEnabled = true
//    mTitle.setSingleLine()
//    mTitle.marqueeRepeatLimit = -1
//    mTitle.isSelected = true
//    mTitle.setHorizontallyScrolling(true)
//}

//val launcherClass = loadClass("com.miui.home.launcher.Launcher")
//val shortcutInfoClass = loadClass("com.miui.home.launcher.ShortcutInfo")
//
//runCatching {
//    "com.miui.home.launcher.ItemIcon".hookAfterMethod(
//        classLoader,
//        "onFinishInflate"
//    ) {
//        val mTitle = it.thisObject.getObjectField("mTitle") as TextView
//        ScrollIconName.mTitleScrolling(mTitle)
//    }
//    "com.miui.home.launcher.maml.MaMlWidgetView".hookAfterMethod(
//        classLoader,
//        "onFinishInflate"
//    ) {
//        val mTitle = it.thisObject.getObjectField("mTitleTextView") as TextView
//        ScrollIconName.mTitleScrolling(mTitle)
//    }
//    "com.miui.home.launcher.LauncherMtzGadgetView".hookAfterMethod(
//        classLoader,
//        "onFinishInflate"
//    ) {
//        val mTitle = it.thisObject.getObjectField("mTitleTextView") as TextView
//        ScrollIconName.mTitleScrolling(mTitle)
//    }
//    "com.miui.home.launcher.LauncherWidgetView".hookAfterMethod(
//        classLoader,
//        "onFinishInflate"
//    ) {
//        val mTitle = it.thisObject.getObjectField("mTitleTextView") as TextView
//        ScrollIconName.mTitleScrolling(mTitle)
//    }
//    "com.miui.home.launcher.ShortcutIcon".hookAfterMethod(
//        classLoader,
//        "fromXml",
//        Int::class.javaPrimitiveType,
//        launcherClass,
//        ViewGroup::class.java,
//        shortcutInfoClass
//    ) {
//        val buddyIconView = it.args[3].callMethod("getBuddyIconView", it.args[2]) as View
//        val mTitle = buddyIconView.getObjectField("mTitle") as TextView
//        ScrollIconName.mTitleScrolling(mTitle)
//    }
//    "com.miui.home.launcher.ShortcutIcon".hookAfterMethod(
//        classLoader,
//        "createShortcutIcon",
//        Int::class.javaPrimitiveType,
//        launcherClass,
//        ViewGroup::class.java
//    ) {
//        val buddyIcon = it.result as View
//        val mTitle = buddyIcon.getObjectField("mTitle") as TextView
//        ScrollIconName.mTitleScrolling(mTitle)
//    }
//    "com.miui.home.launcher.common.Utilities".hookAfterMethod(
//        classLoader,
//        "adaptTitleStyleToWallpaper",
//        Context::class.java,
//        TextView::class.java,
//        Int::class.javaPrimitiveType,
//        Int::class.javaPrimitiveType
//    ) {
//        val mTitle = it.args[1] as TextView
//        if (mTitle.id == mTitle.resources.getIdentifier(
//                "icon_title",
//                "id",
//                "com.miui.home"
//            )
//        ) {
//            ScrollIconName.mTitleScrolling(mTitle)
//        }
//    }
//}