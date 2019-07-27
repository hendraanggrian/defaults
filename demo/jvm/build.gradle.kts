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

    implementation(hendraanggrian("ktfx", "ktfx", VERSION_KTFX))
    implementation(apache("commons", "commons-lang3", VERSION_COMMONS_LANG))
    implementation(project(":$RELEASE_ARTIFACT-jvm"))
    kapt(project(":$RELEASE_ARTIFACT-compiler"))

    testImplementation(kotlin("test-junit", VERSION_KOTLIN))
    testImplementation(truth())
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}