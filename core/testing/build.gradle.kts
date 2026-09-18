plugins {
    alias(libs.plugins.guessmydraw.android.library)
}

android {
    namespace = "com.neacoding.testing"
}

dependencies {
    api(project(":core:domain"))

    api(libs.kotlinx.coroutines.test)
    api(platform(libs.junit.bom))
    api(libs.junit.jupiter)
}
