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
    compileSdk = 34
    buildToolsVersion = "34.0.0"
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
        targetSdk = 34
        versionCode = 25
        versionName = "3.06"
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro", "proguard-log.pro"
            )
        }
        debug {
            versionNameSuffix = "-debug-" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(
                LocalDateTime.now()
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_20
        targetCompatibility = JavaVersion.VERSION_20
    }

    kotlinOptions {
        jvmTarget = "20"
    }

    kotlin {
        sourceSets.all {
            languageSettings {
                languageVersion = "2.0"
            }
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/**"
            excludes += "/kotlin/**"
            excludes += "/*.txt"
            excludes += "/*.bin"
            excludes += "/*.json"
        }
        dex {
            useLegacyPackaging = true
        }
        applicationVariants.all {
            outputs.all {
                (this as BaseVariantOutputImpl).outputFileName =
                    "StarVoyager-$versionName-$name.apk"
            }
        }
    }
}

dependencies {
    compileOnly(libs.xposed)
    implementation(project(":blockmiui"))
    implementation(libs.core.ktx)
    implementation(libs.constraintlayout)
    implementation(libs.jpinyin)
    implementation(libs.lsposed.hidden.api.bypass)
    implementation(libs.ezxhelper)
    implementation(libs.dexkit)
}