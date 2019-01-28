include(RELEASE_ARTIFACT)
include("$RELEASE_ARTIFACT-jre")
include("$RELEASE_ARTIFACT-android")
include("$RELEASE_ARTIFACT-annotations")
include("$RELEASE_ARTIFACT-compiler")

include(":demo:jre")
include(":demo:android")