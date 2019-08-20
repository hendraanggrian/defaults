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
    implementation(project(":$RELEASE_ARTIFACT-adapters:jvm"))
    implementation(project(":$RELEASE_ARTIFACT-loggers:log4j"))
    implementation(project(":$RELEASE_ARTIFACT-loggers:slf4j"))
    kapt(project(":$RELEASE_ARTIFACT-compiler"))

    implementation(hendraanggrian("ktfx", "ktfx", VERSION_KTFX))
    implementation(apache("commons", "commons-lang3", VERSION_COMMONS_LANG))

    testImplementation(kotlin("test-junit", VERSION_KOTLIN))
    testImplementation(truth())
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}