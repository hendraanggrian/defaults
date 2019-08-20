buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", VERSION_KOTLIN))
        classpath(android())
        classpath(dokka())
        classpath(dokka("android"))
        classpath(gitPublish())
        classpath(bintray())
        classpath(bintrayRelease())
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
    tasks.withType<Delete> {
        delete(projectDir.resolve("out"))
    }
}

tasks {
    register<Delete>("clean") {
        delete(buildDir)
    }
    named<Wrapper>("wrapper") {
        gradleVersion = VERSION_GRADLE
    }
}
