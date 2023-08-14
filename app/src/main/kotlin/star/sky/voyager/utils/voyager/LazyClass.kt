package star.sky.voyager.utils.voyager

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass

object LazyClass {
    val FeatureParserCls by lazy {
        loadClass("miui.util.FeatureParser")
    }

    val SystemProperties by lazy {
        loadClass("android.os.SystemProperties")
    }

    val MiuiBuildCls by lazy {
        loadClass("miui.os.Build")
    }
}