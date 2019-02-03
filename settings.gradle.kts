include(RELEASE_ARTIFACT) // base concept without a builder
include("$RELEASE_ARTIFACT-annotations") // jvm-7 annotation used in client and compiler
include("$RELEASE_ARTIFACT-compiler") // generates local settings binding
includeAll("$RELEASE_ARTIFACT-features")

include("website")

includeAll("demo")

fun includeAll(path: String) = file(path)
    .listFiles()
    .filter { it.isDirectory }
    .forEach { include("$path:${it.name}") }