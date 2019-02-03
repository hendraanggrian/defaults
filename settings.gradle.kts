include(RELEASE_ARTIFACT)
include("$RELEASE_ARTIFACT-android")
include("$RELEASE_ARTIFACT-annotations")
include("$RELEASE_ARTIFACT-compiler")

include("website")

includeAll("demo")

fun includeAll(path: String) = file(path)
    .listFiles()
    .filter { it.isDirectory }
    .forEach { include("$path:${it.name}") }