import com.neacoding.convention.configureKotlinJvm
import com.neacoding.convention.libs
import com.neacoding.convention.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Pure Kotlin/JVM module with no Android dependency — used for domain layers, which must stay
 * framework-free so they remain trivially unit-testable (and portable to KMP later).
 */
class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.jvm")

        configureKotlinJvm()

        dependencies {
            add("implementation", libs.library("kotlinx-coroutines-core"))
        }
    }
}
