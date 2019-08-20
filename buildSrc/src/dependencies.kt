import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.plugin.use.PluginDependenciesSpec

fun DependencyHandler.square(module: String, version: String) = "com.squareup:$module:$version"

fun DependencyHandler.google(module: String, version: String, vararg suffixes: String) = when {
    suffixes.isEmpty() -> "com.google.${module.substringBefore("-")}:$module:$version"
    else -> "com.google${StringBuilder().apply { suffixes.forEach { append(".$it") } }}:$module:$version"
}

fun DependencyHandler.kotlinx(
    module: String,
    version: String? = null
) = "org.jetbrains.kotlinx:kotlinx-$module${version?.let { ":$it" } ?: ""}"

fun DependencyHandler.dokka(module: String? = null) =
    "org.jetbrains.dokka:dokka-${module.wrap { "$it-" }}gradle-plugin:$VERSION_DOKKA"

fun PluginDependenciesSpec.dokka(module: String? = null) =
    id("org.jetbrains.dokka${module.wrap { "-$it" }}")

fun DependencyHandler.android() = "com.android.tools.build:gradle:$VERSION_ANDROID_PLUGIN"
fun PluginDependenciesSpec.android(submodule: String) = id("com.android.$submodule")

fun DependencyHandler.androidx(
    repository: String,
    module: String = repository,
    version: String = VERSION_ANDROIDX
): String = "androidx.$repository:$module:$version"

fun DependencyHandler.material(version: String = VERSION_ANDROIDX) =
    "com.google.android.material:material:$version"

fun DependencyHandler.snappydb() = "com.snappydb:snappydb-lib:$VERSION_SNAPPYDB"
fun DependencyHandler.kyro() = "com.esotericsoftware.kryo:kryo:$VERSION_KYRO"

fun DependencyHandler.hendraanggrian(module: String, version: String) =
    "com.hendraanggrian:$module:$version"

fun DependencyHandler.hendraanggrian(
    repository: String,
    module: String,
    version: String
) = "com.hendraanggrian.$repository:$module:$version"

fun DependencyHandler.apache(repo: String, module: String, version: String) =
    "org.apache.$repo:$module:$version"

fun DependencyHandler.slf4j() = "org.slf4j:slf4j-api:$VERSION_SLF4J"

fun DependencyHandler.testFx(module: String) = "org.testfx:testfx-$module:$VERSION_TESTFX"

fun DependencyHandler.truth() = "com.google.truth:truth:$VERSION_TRUTH"

fun DependencyHandler.shadow() = "com.github.jengelman.gradle.plugins:shadow:$VERSION_SHADOW"

fun DependencyHandler.gitPublish() = "org.ajoberstar:gradle-git-publish:$VERSION_GIT_PUBLISH"
inline val PluginDependenciesSpec.`git-publish` get() = id("org.ajoberstar.git-publish")

fun DependencyHandler.bintray() = "com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4"
inline val PluginDependenciesSpec.bintray get() = id("com.jfrog.bintray")

fun DependencyHandler.bintrayRelease() = "com.novoda:bintray-release:0.9"
inline val PluginDependenciesSpec.`bintray-release` get() = id("com.novoda.bintray-release")

fun DependencyHandler.ktlint(module: String? = null) = when (module) {
    null -> "com.pinterest:ktlint:$VERSION_KTLINT"
    else -> "com.pinterest.ktlint:ktlint-$module:$VERSION_KTLINT"
}

private fun String?.wrap(wrapper: (String) -> String) = this?.let(wrapper).orEmpty()
