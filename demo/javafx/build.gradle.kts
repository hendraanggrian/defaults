plugins {
    kotlin("jvm")
    kotlin("kapt")
    application
}

application.mainClassName = "com.example.prefs.DemoApplication"

sourceSets {
    get("main").java.srcDir("src")
    get("test").java.srcDir("tests/src")
}

dependencies {
    api(kotlin("stdlib", VERSION_KOTLIN))

    implementation(project(":$RELEASE_ARTIFACT-jvm"))
    kapt(project(":$RELEASE_ARTIFACT-compiler"))

    implementation(hendraanggrian("ktfx", "ktfx", VERSION_KTFX))
    implementation(apache("commons", "commons-lang3", VERSION_COMMONS_LANG))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}