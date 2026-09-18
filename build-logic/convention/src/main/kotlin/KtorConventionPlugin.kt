import com.neacoding.convention.bundle
import com.neacoding.convention.libs
import com.neacoding.convention.library
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KtorConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("guessmydraw.serialization")

        val ktorBom = libs.library("ktor-bom")
        dependencies {
            add("implementation", platform(ktorBom))
            add("implementation", libs.bundle("ktor"))

            add("testImplementation", platform(ktorBom))
            add("testImplementation", libs.library("ktor-client-mock"))
        }
    }
}
