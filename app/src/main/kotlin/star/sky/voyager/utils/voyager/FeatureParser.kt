package star.sky.voyager.utils.voyager

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass

object FeatureParser {
    val FeatureParserCls by lazy {
        loadClass("miui.util.FeatureParser")
    }
}