package star.sky.voyager.utils.yife

import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam
import io.luckypray.dexkit.DexKitBridge

object DexKit {
    private lateinit var hostDir: String
    lateinit var dexKitBridge: DexKitBridge

    fun initDexKit(loadPackageParam: LoadPackageParam) {
        hostDir = loadPackageParam.appInfo.sourceDir
    }

    fun loadDexKit() {
        if (this::dexKitBridge.isInitialized) return
        System.loadLibrary("dexkit")
        DexKitBridge.create(hostDir)?.let {
            dexKitBridge = it
        }
    }

    fun closeDexKit() {
        if (this::dexKitBridge.isInitialized) dexKitBridge.close()
    }
}