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
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
    tasks {
        withType<Delete> {
            delete(projectDir.resolve("out"))
        }
        /* withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        } */
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