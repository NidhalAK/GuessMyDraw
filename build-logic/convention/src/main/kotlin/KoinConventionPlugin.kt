import com.neacoding.convention.bundle
import com.neacoding.convention.libs
import com.neacoding.convention.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val koinBom = libs.library("koin-bom")
        dependencies {
            add("implementation", platform(koinBom))
            add("implementation", libs.bundle("koin"))

            add("testImplementation", platform(koinBom))
            add("testImplementation", libs.library("koin-test"))
        }
    }
}
