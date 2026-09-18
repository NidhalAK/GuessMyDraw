plugins {
    `kotlin-dsl`
}

group = "com.neacoding.buildlogic"

// `kotlin-dsl` derives its Kotlin jvmTarget from the Java toolchain, so setting
// the toolchain alone keeps the convention plugins aligned with the app modules.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "guessmydraw.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "guessmydraw.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "guessmydraw.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "guessmydraw.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "guessmydraw.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("jvmLibrary") {
            id = "guessmydraw.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("koin") {
            id = "guessmydraw.koin"
            implementationClass = "KoinConventionPlugin"
        }
        register("ktor") {
            id = "guessmydraw.ktor"
            implementationClass = "KtorConventionPlugin"
        }
        register("room") {
            id = "guessmydraw.room"
            implementationClass = "RoomConventionPlugin"
        }
        register("serialization") {
            id = "guessmydraw.serialization"
            implementationClass = "SerializationConventionPlugin"
        }
    }
}
