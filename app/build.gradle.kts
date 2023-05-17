import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "star.sky.voyager"
    compileSdkPreview = "UpsideDownCake"
    buildToolsVersion = "34.0.0 rc4"
    ndkVersion = "25.2.9519653"

    buildFeatures {
        prefab = true
        buildConfig = true
        viewBinding = true
        resValues = true
    }

    defaultConfig {
        applicationId = "star.sky.voyager"
        minSdk = 33
        targetSdk = 33
        versionCode = 2
        versionName = "1.1"
        buildConfigField("String", "BUILD_TIME", "\"${System.currentTimeMillis()}\"")
    }

    splits {
        abi {
            isEnable = true
            reset()
            include("arm64-v8a")
            isUniversalApk = false
        }
    }

    buildTypes {
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
        }
        debug {
            versionNameSuffix = "-debug-" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(
                LocalDateTime.now()
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources {
            excludes += "/META-INF/**"
            excludes += "/kotlin/**"
            excludes += "/*.txt"
            excludes += "/*.bin"
        }
        dex {
            useLegacyPackaging = true
        }
    }

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }

    applicationVariants.all {
        outputs.all {
            (this as BaseVariantOutputImpl).outputFileName = "StarVoyager-$versionName-$name.apk"
        }
    }
}

dependencies {
    implementation(project(":blockmiui"))
    implementation(libs.core.ktx)
    implementation(libs.lsposed.hidden.api.bypass)
    implementation(libs.ezxhelper)
    implementation(libs.dexkit)
    compileOnly(libs.xposed)
}