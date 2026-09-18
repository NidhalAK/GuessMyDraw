package com.neacoding.convention

import org.gradle.api.JavaVersion

/**
 * Single source of truth for values that must stay identical across every module.
 * Changing an SDK level or the JVM target here propagates to the whole build.
 */
object ProjectConfig {
    const val APPLICATION_ID = "com.neacoding.guessmydraw"

    const val COMPILE_SDK = 37
    const val MIN_SDK = 24
    const val TARGET_SDK = 37

    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"

    const val TEST_RUNNER = "androidx.test.runner.AndroidJUnitRunner"

    const val JVM_TOOLCHAIN = 17
    val JAVA_VERSION: JavaVersion = JavaVersion.VERSION_17
}
