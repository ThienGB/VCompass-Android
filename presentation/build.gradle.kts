plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.vcompass.presentation"
    compileSdk = 36

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)

        }
    }

    buildFeatures{
        buildConfig = true
    }

    flavorDimensions += "env"

    productFlavors {
        create("stag") {
            dimension = "env"
        }

        create("uat") {
            dimension = "env"
        }

        create("prod") {
            dimension = "env"
        }
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)
    implementation(libs.viewmodel.lifecyle)
    implementation(libs.viewmodel.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.paging.common)
}