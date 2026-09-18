import com.neacoding.convention.libs
import com.neacoding.convention.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Everything a `:feature:<name>:presentation` module needs: Android library + Compose + Koin,
 * plus the three core modules every feature is allowed to depend on.
 *
 * Features must never depend on other features; only on `:core:*`.
 */
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("guessmydraw.android.library.compose")
        pluginManager.apply("guessmydraw.koin")

        dependencies {
            add("implementation", project(":core:domain"))
            add("implementation", project(":core:presentation"))
            add("implementation", project(":core:design-system"))

            add("implementation", libs.library("androidx-lifecycle-viewmodel-compose"))
            add("implementation", libs.library("androidx-lifecycle-runtime-compose"))
            add("implementation", libs.library("androidx-navigation-compose"))
            add("implementation", libs.library("kotlinx-coroutines-android"))
        }
    }
}
