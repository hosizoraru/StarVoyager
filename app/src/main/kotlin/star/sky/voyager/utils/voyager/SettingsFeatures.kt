package star.sky.voyager.utils.voyager

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass

object SettingsFeatures {
    val SettingsFeaturesCls by lazy {
        loadClass("com.android.settings.utils.SettingsFeatures")
    }
}