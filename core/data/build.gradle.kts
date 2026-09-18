plugins {
    alias(libs.plugins.guessmydraw.android.library)
    alias(libs.plugins.guessmydraw.ktor)
    alias(libs.plugins.guessmydraw.koin)
}

android {
    namespace = "com.neacoding.data"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://api.guessmydraw.app\"")
    }
}

dependencies {
    api(project(":core:domain"))

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kermit)
}
