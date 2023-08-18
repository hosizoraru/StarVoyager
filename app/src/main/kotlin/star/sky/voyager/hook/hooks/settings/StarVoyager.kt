package star.sky.voyager.hook.hooks.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.UserHandle
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.moduleRes
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import de.robv.android.xposed.XposedHelpers
import star.sky.voyager.BuildConfig
import star.sky.voyager.R
import star.sky.voyager.activity.MainActivity
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.MiuiSettingsCls

@SuppressLint("DiscouragedApi")
object StarVoyager : HookRegister() {
    override fun init() = hasEnable("star_voyager") {
        val preferenceActivityCls =
            loadClass("com.android.settingslib.miuisettings.preference.PreferenceActivity\$Header")

        MiuiSettingsCls.methodFinder()
            .filterByName("updateHeaderList")
            .first().createHook {
                after { param ->
//                    val mContext = (param.thisObject as Activity).baseContext
//                    val modRes = mContext.resources
                    val mIntent = Intent()
                    mIntent.putExtra("isDisplayHomeAsUpEnabled", true)
                    mIntent.setClassName(
                        BuildConfig.APPLICATION_ID,
                        MainActivity::class.java.canonicalName!!
                    )
                    val header = XposedHelpers.newInstance(preferenceActivityCls)
                    XposedHelpers.setLongField(header, "id", 666)
                    XposedHelpers.setObjectField(header, "intent", mIntent)
                    XposedHelpers.setObjectField(
                        header,
                        "title",
                        moduleRes.getString(R.string.app_name)
                    )
                    val bundle = Bundle()
                    val users = ArrayList<UserHandle>()
                    users.add(XposedHelpers.newInstance(UserHandle::class.java, 0) as UserHandle)
                    bundle.putParcelableArrayList("header_user", users)
                    XposedHelpers.setObjectField(header, "extras", bundle)
                    val headers = param.args[0] as MutableList<Any>
                    var position = 0
                    for (head in headers) {
                        position++
                        val id = XposedHelpers.getLongField(head, "id")
                        if (id == -1L) {
                            headers.add(position - 1, header)
                        }
                    }
                    if (headers.size > 25) {
                        headers.add(25, header)
                    } else {
                        headers.add(header)
                    }
                }
            }
    }
}