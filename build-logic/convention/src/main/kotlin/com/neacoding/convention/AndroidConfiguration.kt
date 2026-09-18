package com.neacoding.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

/**
 * Baseline Android configuration shared by the application module and every library module.
 *
 * AGP 9 ships built-in Kotlin support, so the Kotlin plugin is already applied by AGP and we
 * only configure its extension rather than applying `org.jetbrains.kotlin.android` ourselves.
 *
 * Note: on [CommonExtension], `defaultConfig`, `buildFeatures` and `buildTypes` are read-only
 * properties (the lambda-taking overloads only exist on the concrete extensions), hence the
 * property-access style below.
 */
internal fun Project.configureAndroidCommon(commonExtension: CommonExtension) {
    commonExtension.compileSdk = ProjectConfig.COMPILE_SDK

    commonExtension.defaultConfig.apply {
        minSdk = ProjectConfig.MIN_SDK
        testInstrumentationRunner = ProjectConfig.TEST_RUNNER
    }

    commonExtension.compileOptions.apply {
        sourceCompatibility = ProjectConfig.JAVA_VERSION
        targetCompatibility = ProjectConfig.JAVA_VERSION
    }

    commonExtension.testOptions.unitTests {
        isIncludeAndroidResources = true
        all { test: Test -> test.useJUnitPlatform() }
    }

    extensions.configure<KotlinAndroidProjectExtension>("kotlin") {
        jvmToolchain(ProjectConfig.JVM_TOOLCHAIN)
    }

    configureUnitTesting()
}

/** Pure Kotlin/JVM module baseline: toolchain, JUnit5 platform and coroutines. */
internal fun Project.configureKotlinJvm() {
    extensions.configure<KotlinJvmProjectExtension>("kotlin") {
        jvmToolchain(ProjectConfig.JVM_TOOLCHAIN)
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }

    configureUnitTesting()
}

/**
 * JUnit5 + AssertK + Turbine + coroutines-test, as mandated by the project testing conventions.
 * Instrumented tests keep JUnit4, which is what `ComposeTestRule` requires.
 */
internal fun Project.configureUnitTesting() {
    dependencies {
        add("testImplementation", platform(libs.library("junit-bom")))
        add("testImplementation", libs.library("junit-jupiter"))
        add("testRuntimeOnly", libs.library("junit-platform-launcher"))
        add("testImplementation", libs.library("assertk"))
        add("testImplementation", libs.library("turbine"))
        add("testImplementation", libs.library("kotlinx-coroutines-test"))
        add("testImplementation", libs.library("mockk"))
    }
}

/** Compose compiler, BOM-aligned Compose dependencies, tooling and UI-test wiring. */
internal fun Project.configureCompose(commonExtension: CommonExtension) {
    pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

    commonExtension.buildFeatures.compose = true

    val composeBom = libs.library("androidx-compose-bom")
    dependencies {
        add("implementation", platform(composeBom))
        add("implementation", libs.bundle("compose"))
        add("debugImplementation", libs.library("androidx-compose-ui-tooling"))
        add("debugImplementation", libs.library("androidx-compose-ui-test-manifest"))

        add("androidTestImplementation", platform(composeBom))
        add("androidTestImplementation", libs.library("androidx-compose-ui-test-junit4"))
        add("androidTestImplementation", libs.library("androidx-junit"))
    }
}
