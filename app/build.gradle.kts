import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    autowire("android-application")
    autowire("kotlin-android")
    autowire("kotlin-serialization")
    autowire("flexi-locale")
}

val apkId = "StarVoyager"

android {
    namespace = "star.sky.voyager"
    compileSdk = 34
    buildToolsVersion = "35.0.0-rc1"
    ndkVersion = "26.2.11394342"

    flexiLocale {
        isEnable = true
    }

    buildFeatures {
        prefab = true
        buildConfig = true
        viewBinding = true
        resValues = true
    }

    defaultConfig {
        applicationId = "star.sky.voyager"
        minSdk = 31
        targetSdk = 34
        versionCode = 32
        versionName = "3.71"
        buildConfigField("String", "BUILD_TIME", "\"${System.currentTimeMillis()}\"")
    }

    splits {
        abi {
            isEnable = true
            reset()
            //noinspection ChromeOsAbiSupport
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

    androidResources {
        additionalParameters += "--allow-reserved-package-id"
        additionalParameters += "--package-id"
        additionalParameters += "0x45"
        generateLocaleConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_20
        targetCompatibility = JavaVersion.VERSION_20
    }

    kotlinOptions {
        jvmTarget = "20"
    }

//    kotlin {
//        sourceSets.all {
//            languageSettings {
//                languageVersion = "2.0"
//            }
//        }
//    }

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
//                    "StarVoyager-$versionName-$name.apk"
                    "${apkId}_$versionName.apk"
            }
        }
//        applicationVariants.configureEach {
//            outputs.configureEach {
//                if (this is BaseVariantOutputImpl) {
//                    outputFileName = "${rootProject.name}_${versionName}.${outputFileName.substringAfterLast('.')}"
//                }
//            }
//        }
    }
}

dependencies {
    compileOnly(de.robv.android.xposed.api)
//    compileOnly(libs.dev.rikka.hidden.stub)
    implementation(project(":blockmiui"))
    implementation(androidx.core.core.ktx)
    implementation(androidx.constraintlayout.constraintlayout)
    implementation(org.luckypray.dexkit)
    implementation(com.github.kyuubiran.ezXHelper)
    implementation(io.github.ranlee1.jpinyin)
    implementation(org.lsposed.hiddenapibypass.hiddenapibypass)
//    implementation(libs.dev.rikka.hidden.compat)
}