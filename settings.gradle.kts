@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("com.highcapable.sweetdependency") version "1.0.1"
}

rootProject.name = "StarSkyVoyager"
include(":app")
include(":blockmiui")
