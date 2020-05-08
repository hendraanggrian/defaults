group = RELEASE_GROUP
version = RELEASE_VERSION

plugins {
    `java-library`
    kotlin("jvm")
    `bintray-release`
}

sourceSets {
    get("main").java.srcDir("src")
    get("test").java.srcDir("tests/src")
}

dependencies {
    api(project(":$RELEASE_ARTIFACT-annotations"))

    testImplementation(kotlin("stdlib", VERSION_KOTLIN))
    testImplementation(kotlin("test-junit", VERSION_KOTLIN))
    testImplementation(google("truth", "truth", VERSION_TRUTH))
}

publish {
    bintrayUser = BINTRAY_USER
    bintrayKey = BINTRAY_KEY
    dryRun = false
    repoName = RELEASE_ARTIFACT

    userOrg = RELEASE_USER
    groupId = RELEASE_GROUP
    artifactId = RELEASE_ARTIFACT
    publishVersion = RELEASE_VERSION
    desc = RELEASE_DESC
    website = RELEASE_WEB
}