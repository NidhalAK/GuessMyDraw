package com.neacoding.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/** Access to the root `libs.versions.toml` catalog from inside a convention plugin. */
internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun VersionCatalog.library(alias: String) = findLibrary(alias).orElseThrow {
    IllegalStateException("Missing library alias '$alias' in libs.versions.toml")
}

internal fun VersionCatalog.bundle(alias: String) = findBundle(alias).orElseThrow {
    IllegalStateException("Missing bundle alias '$alias' in libs.versions.toml")
}
