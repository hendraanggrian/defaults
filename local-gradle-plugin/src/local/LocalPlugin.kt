package local

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke

class LocalPlugin : Plugin<Project> {

    companion object {
        const val GROUP_NAME = "local"
    }

    override fun apply(project: Project) {
        val ext = project.extensions.create<LocalExtension>(GROUP_NAME)
        project.tasks {
        }
    }
}
