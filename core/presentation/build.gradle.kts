plugins {
    alias(libs.plugins.guessmydraw.android.library.compose)
}

android {
    namespace = "com.neacoding.presentation"
}

dependencies {
    api(project(":core:domain"))

    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.kotlinx.coroutines.android)
}
