package star.sky.voyager.utils.voyager

import star.sky.voyager.utils.key.XSPUtils.getBoolean

object SetKeyMap {
    fun setKeyMap(properties: Map<String, () -> Unit>) {
        properties.forEach { (key, action) ->
            if (getBoolean(key, false)) {
                action()
            }
        }
    }

    fun setKeyMapNot(properties: Map<String, () -> Unit>) {
        properties.forEach { (key, action) ->
            if (getBoolean(key, true).not()) {
                action()
            }
        }
    }

    inline fun whenV(key: String, block: WhenVContext.() -> Unit) {
        val context = WhenVContext(key)
        context.block()
    }

    class WhenVContext(private val key: String) {
        infix fun Boolean.then(block: () -> Unit) {
            if (getBoolean(key, false) == this) {
                block()
            }
        }
    }

    inline fun whenT(vararg keys: String, block: () -> Unit) {
        if (keys.any { getBoolean(it, false) }) {
            block()
        }
    }
}