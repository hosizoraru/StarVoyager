package star.sky.voyager.hook.hooks.mediaeditor

import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import star.sky.voyager.utils.init.HookRegister
import star.sky.voyager.utils.key.XSPUtils.getString
import star.sky.voyager.utils.key.hasEnable
import star.sky.voyager.utils.voyager.LazyClass.SystemProperties

object MarketName : HookRegister() {
    private val ProductMarketName by lazy {
        getString("product_market_name", "Xiaomi 13 Ultra")
    }

    override fun init() = hasEnable("market_name") {
        SystemProperties.methodFinder()
//            .filterByName("get")
            .filterByParamCount(2)
            .filterByParamTypes(String::class.java, String::class.java)
            .toList().createHooks {
                before {
                    if (it.args[0] == "ro.product.marketname") {
                        it.args[1] = ProductMarketName
                    }
                }
            }

        SystemProperties.methodFinder()
            .filterByName("get")
            .filterByParamTypes(String::class.java)
            .toList().createHooks {
                before {
                    if (it.args[0] == "ro.product.marketname") {
                        it.result = ProductMarketName
                    }
                }
            }
    }
}