package star.sky.voyager.utils.yife

import com.github.kyuubiran.ezxhelper.ClassUtils.getStaticObjectOrNullAs
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass

/**
 * 获取系统信息
 */
object Build {
    private val clazzMiuiBuild by lazy {
        loadClass("miui.os.Build")
    }

    /**
     * 设备是否为平板
     */
    val IS_TABLET by lazy {
        getStaticObjectOrNullAs<Boolean>(clazzMiuiBuild, "IS_TABLET") ?: false
    }

    /**
     * 是否为国际版系统
     */
    val IS_INTERNATIONAL_BUILD by lazy {
        getStaticObjectOrNullAs<Boolean>(clazzMiuiBuild, "IS_INTERNATIONAL_BUILD") ?: false
    }

    val IS_GLOBAL_BUILD by lazy {
        getStaticObjectOrNullAs<Boolean>(clazzMiuiBuild, "IS_GLOBAL_BUILD") ?: false
    }
}