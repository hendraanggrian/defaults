package local

import com.hendraanggrian.javapoet.buildJavaFile
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.IOException

open class LocalTask : DefaultTask() {

    @Input var packageName: String = ""

    @Input lateinit var map: Map<String, Map<String, Class<*>>>

    @TaskAction
    @Throws(IOException::class)
    fun write() {
        require(packageName.isNotBlank()) { "Package name cannot be null" }
        buildJavaFile(packageName) {
            
            map.forEach { className, value ->

            }
        }
    }
}