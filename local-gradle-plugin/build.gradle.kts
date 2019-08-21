plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    dokka()
    bintray
    `bintray-release`
}

group = RELEASE_GROUP
version = RELEASE_VERSION

sourceSets {
    get("main").java.srcDir("src")
    get("test").java.srcDir("tests/src")
}

gradlePlugin {
    (plugins) {
        register(RELEASE_ARTIFACT) {
            id = RELEASE_GROUP
            implementationClass = "$RELEASE_ARTIFACT.LocalPlugin"
        }
    }
}

val configuration = configurations.register("ktlint")

dependencies {
    implementation(kotlin("stdlib", VERSION_KOTLIN))
    implementation(project(":$RELEASE_ARTIFACT"))

    testImplementation(kotlin("test-junit"))

    configuration {
        invoke(ktlint())
    }
}

tasks {
    register("deploy") {
        dependsOn("build")
        projectDir.resolve("build/libs")?.listFiles()?.forEach {
            it.renameTo(File(rootDir.resolve("demo"), it.name))
        }
    }

    val ktlint = register("ktlint", JavaExec::class) {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        inputs.dir("src")
        outputs.dir("src")
        description = "Check Kotlin code style."
        classpath(configuration.get())
        main = "com.pinterest.ktlint.Main"
        args("src/**/*.kt")
    }
    "check" {
        dependsOn(ktlint.get())
    }
    register("ktlintFormat", JavaExec::class) {
        group = "formatting"
        inputs.dir("src")
        outputs.dir("src")
        description = "Fix Kotlin code style deviations."
        classpath(configuration.get())
        main = "com.pinterest.ktlint.Main"
        args("-F", "src/**/*.kt")
    }

    named<org.jetbrains.dokka.gradle.DokkaTask>("dokka") {
        outputDirectory = "$buildDir/docs"
        doFirst { file(outputDirectory).deleteRecursively() }
    }
}

publishKotlinFix()
publish {
    bintrayUser = BINTRAY_USER
    bintrayKey = BINTRAY_KEY
    dryRun = false

    userOrg = RELEASE_USER
    groupId = RELEASE_GROUP
    artifactId = RELEASE_ARTIFACT
    publishVersion = RELEASE_VERSION
    desc = RELEASE_DESC
    website = RELEASE_WEBSITE
}