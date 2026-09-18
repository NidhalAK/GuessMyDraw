plugins {
    alias(libs.plugins.guessmydraw.android.application.compose)
    alias(libs.plugins.guessmydraw.koin)
}

android {
    namespace = "com.neacoding.guessmydraw"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:presentation"))
    implementation(project(":core:design-system"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.kotlinx.coroutines.android)

    androidTestImplementation(libs.androidx.espresso.core)
}
