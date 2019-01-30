plugins {
    android("application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(SDK_TARGET)
    buildToolsVersion(BUILD_TOOLS)
    defaultConfig {
        minSdkVersion(SDK_MIN)
        targetSdkVersion(SDK_TARGET)
        applicationId = "$RELEASE_GROUP.demo"
        versionName = RELEASE_VERSION
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDirs("src")
            res.srcDir("res")
            resources.srcDir("src")
        }
    }
    applicationVariants.all {
        generateBuildConfigProvider?.configure {
            enabled = false
        }
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    packagingOptions {
        exclude("META-INF/services/javax.annotation.processing.Processor")
    }
    lintOptions {
        isAbortOnError = false
    }
}

dependencies {
    api(kotlin("stdlib", VERSION_KOTLIN))

    implementation(apache("commons-lang3", VERSION_COMMONS_LANG))
    implementation(project(":$RELEASE_ARTIFACT-platforms:$RELEASE_ARTIFACT-android"))
    kapt(project(":$RELEASE_ARTIFACT-compiler"))

    implementation(androidx("multidex", version = VERSION_MULTIDEX))
    implementation(androidx("core", "core-ktx", "$VERSION_ANDROIDX-alpha03"))
    implementation(androidx("appcompat", version = "$VERSION_ANDROIDX-alpha01"))
    implementation(androidx("preference", version = "$VERSION_ANDROIDX-alpha01"))
    implementation(androidx("coordinatorlayout", version = "$VERSION_ANDROIDX-alpha01"))
    implementation(androidx("recyclerview", version = "$VERSION_ANDROIDX-alpha01"))
    implementation(material("$VERSION_ANDROIDX-alpha02"))

    testImplementation(kotlin("test", VERSION_KOTLIN))
    testImplementation(junit())
    testImplementation(truth())
}