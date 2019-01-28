group = RELEASE_GROUP
version = RELEASE_VERSION

plugins {
    kotlin("jvm")
    kotlin("kapt")
}

sourceSets {
    get("main").java.srcDir("src")
    get("test").java.srcDir("tests/src")
}

dependencies {
    api(kotlin("stdlib", VERSION_KOTLIN))

    implementation(project(":$RELEASE_ARTIFACT"))
    kapt(project(":$RELEASE_ARTIFACT-compiler"))

    testImplementation(kotlin("test", VERSION_KOTLIN))
    testImplementation(junit())
    testImplementation(truth())
}