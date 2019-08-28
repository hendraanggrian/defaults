plugins {
    `git-publish`
}

gitPublish {
    repoUri = RELEASE_WEBSITE
    branch = "gh-pages"
    contents.from(
        "src",
        *artifacts
            .map { "../$it/build/docs" }
            .toTypedArray()
    )
}

tasks["gitPublishCopy"].dependsOn(
    *artifacts
        .map { it.replace('/', ':') }
        .map { ":$it:dokka" }
        .toTypedArray()
)

// list of artifacts with Dokka
val artifacts: List<String>
    get() = listOf(
        RELEASE_ARTIFACT,
        "$RELEASE_ARTIFACT-compiler",
        "$RELEASE_ARTIFACT-jvm",
        "$RELEASE_ARTIFACT-android",
        "$RELEASE_ARTIFACT-android-snappydb"
    )