include(RELEASE_ARTIFACT)
include("$RELEASE_ARTIFACT-annotations")
include("$RELEASE_ARTIFACT-compiler")

include("$RELEASE_ARTIFACT-features:$RELEASE_ARTIFACT-jre")
include("$RELEASE_ARTIFACT-features:$RELEASE_ARTIFACT-android")

include(":demo:jre")
include(":demo:android")