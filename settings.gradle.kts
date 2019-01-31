include(RELEASE_ARTIFACT)
include("$RELEASE_ARTIFACT-annotations")
include("$RELEASE_ARTIFACT-compiler")
includeAll("$RELEASE_ARTIFACT-features")

include("website")

include(":demo:jre")
include(":demo:android")

fun includeAll(path: String) = file(path).listFiles().forEach {
    include("$path:${it.name}")
}