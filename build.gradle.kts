// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    autowire("android-application") apply false
//    alias(libs.plugins.androidLibrary) apply false
    autowire("kotlin-android") apply false
}
true // Needed to make the Suppress annotation work for the plugins block

task("clean") {
    delete(rootProject.buildDir)
}