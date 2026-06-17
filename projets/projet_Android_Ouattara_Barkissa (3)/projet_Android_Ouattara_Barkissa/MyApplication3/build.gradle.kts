// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
}

tasks.configureEach {
    if (name == "mapDebugSourceSetPaths") {
        notCompatibleWithConfigurationCache("Workaround for Gradle issue")
    }
}

