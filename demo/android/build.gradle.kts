plugins {
    android("application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(SDK_TARGET)
    defaultConfig {
        minSdkVersion(SDK_MIN)
        targetSdkVersion(SDK_TARGET)
        applicationId = "$RELEASE_GROUP.demo"
        versionName = RELEASE_VERSION
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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

    implementation(project(":$RELEASE_ARTIFACT"))
    implementation(project(":$RELEASE_ARTIFACT-adapters:android"))
    kapt(project(":$RELEASE_ARTIFACT-compiler"))

    implementation(apache("commons", "commons-lang3", VERSION_COMMONS_LANG))

    implementation(androidx("multidex", version = VERSION_MULTIDEX))
    implementation(androidx("core", "core-ktx", "$VERSION_ANDROIDX-rc02"))
    implementation(androidx("appcompat", version = "$VERSION_ANDROIDX-rc01"))
    implementation(androidx("preference", "preference-ktx", version = "$VERSION_ANDROIDX-rc01"))
    implementation(androidx("coordinatorlayout", version = "$VERSION_ANDROIDX-beta01"))
    implementation(androidx("recyclerview", version = "$VERSION_ANDROIDX-beta01"))
    implementation(material("$VERSION_ANDROIDX-alpha07"))

    testImplementation(kotlin("test-junit", VERSION_KOTLIN))
    testImplementation(truth())
}
