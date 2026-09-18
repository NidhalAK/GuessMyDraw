import com.android.build.api.dsl.ApplicationExtension
import com.neacoding.convention.configureCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("guessmydraw.android.application")

        extensions.configure<ApplicationExtension> {
            configureCompose(this)
        }
    }
}
