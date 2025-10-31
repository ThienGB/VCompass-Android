plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.vcompass.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        buildConfig = true
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

    flavorDimensions += "env"

    productFlavors {
        create("stag") {
            dimension = "env"
            buildConfigField("String", "BASE_URL", "\"https://vcompass-be.onrender.com/\"")
            buildConfigField(
                "String",
                "SOCKET_IO_URL",
                "\"wss://api.accessed.co:8443/message-service/\""
            )
            buildConfigField("String", "SOCKET_IO_HANDSHAKE_PATH", "\"/message-service/socket.io\"")
        }

        create("prod") {
            dimension = "env"
            buildConfigField("String", "BASE_URL", "\"https://vcompass-be.onrender.com/\"")
            buildConfigField(
                "String",
                "SOCKET_IO_URL",
                "\"wss://api.accessed.co:8443/message-service/\""
            )
            buildConfigField("String", "SOCKET_IO_HANDSHAKE_PATH", "\"/message-service/socket.io\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)

        }
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.retrofit.logging)

    implementation(libs.androidx.core.ktx)
    implementation(libs.security.crypto)
    implementation (libs.androidx.datastore.preferences)
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)
}