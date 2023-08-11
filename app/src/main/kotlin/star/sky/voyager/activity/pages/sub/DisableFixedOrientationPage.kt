package star.sky.voyager.activity.pages.sub

import android.content.pm.ApplicationInfo
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.fragment.MIUIFragment
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextSummaryWithSwitchV
import io.github.ranlee1.jpinyin.PinyinFormat
import io.github.ranlee1.jpinyin.PinyinHelper
import star.sky.voyager.utils.yife.SharedPreferences.putStringSet

//@SuppressLint("NonConstantResourceId")
@BMPage("DisableFixedOrientationPage", "禁用固定屏幕方向作用域", hideMenu = false)
class DisableFixedOrientationPage : BasePage() {
    init {
        skipLoadItem = true
    }

    override fun asyncInit(fragment: MIUIFragment) {
        fragment.showLoading()
        runCatching {
            @Suppress("DEPRECATION") val applicationsInfo =
                activity.packageManager.getInstalledApplications(0)
                    .filter { (it.flags and ApplicationInfo.FLAG_SYSTEM) != 1 }
                    .associateWith {
                        val label = it.loadLabel(activity.packageManager).toString()
                        PinyinHelper.convertToPinyinString(label, "", PinyinFormat.WITHOUT_TONE)
                            .lowercase()
                    }.entries.sortedBy { it.value }.map { it.key }
            for (i in applicationsInfo) {
                fragment.addItem(
                    TextSummaryWithSwitchV(TextSummaryV(
                        text = i.loadLabel(activity.packageManager).toString(),
                        tips = i.packageName
                    ), SwitchV("disable_fixed_orientation_" + i.packageName) { switchValue ->
                        @Suppress("DEPRECATION") val packagesInfo1 =
                            MIUIActivity.activity.packageManager.getInstalledApplications(0)
                        val shouldDisableFixedOrientationList = mutableListOf<String>()
                        for (j in packagesInfo1) {
                            if ((j.flags and ApplicationInfo.FLAG_SYSTEM) != 1) {
                                val packageName = j.packageName
                                if (MIUIActivity.safeSP.getBoolean(
                                        "disable_fixed_orientation_$packageName",
                                        false
                                    )
                                ) {
                                    shouldDisableFixedOrientationList.add(packageName)
                                }
                            }
                        }
                        if (switchValue) {
                            val packageName = i.packageName
                            if (!shouldDisableFixedOrientationList.contains(packageName)) {
                                shouldDisableFixedOrientationList.add(packageName)
                            }
                        } else {
                            shouldDisableFixedOrientationList.remove(i.packageName)
                        }
                        MIUIActivity.safeSP.mSP?.putStringSet(
                            "should_disable_fixed_orientation_list",
                            shouldDisableFixedOrientationList.toSet()
                        )
                    })
                )
            }
        }
        fragment.closeLoading()
        fragment.initData()
    }

    override fun onCreate() {}
}