include(RELEASE_ARTIFACT)
include("$RELEASE_ARTIFACT-annotations")
include("$RELEASE_ARTIFACT-compiler")
includeDir("$RELEASE_ARTIFACT-adapters")
includeDir("$RELEASE_ARTIFACT-loggers")
//include("website")
//includeDir("demo")

fun includeDir(path: String) = file(path)
    .listFiles()!!
    .filter { it.isDirectory }
    .forEach { include("$path:${it.name}") }