plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.vcompass"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.vcompass"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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
    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }

    packaging {
        resources.excludes += setOf(
            "META-INF/versions/9/OSGI-INF/MANIFEST.MF",
            "/META-INF/{AL2.0,LGPL2.1}"
        )
    }

    flavorDimensions += "env"

    productFlavors {
        create("stag") {
            dimension = "env"
            resValue("string", "app_name", "STAG-VCompass")
        }

        create("prod") {
            dimension = "env"
            resValue("string", "app_name", "VCompass")
        }
    }

}

dependencies {
    implementation(project(":presentation"))
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidthings)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.ui.test.android)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.compose.testing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    implementation (libs.picasso)
    implementation(libs.coil.compose)
    implementation(libs.reflections)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.navigation.compose)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation (libs.rxjava)
    implementation (libs.rxandroid)
    implementation (libs.accompanist.permissions)
    implementation(libs.gson)
    implementation(libs.kotlinx.serialization.json)
    implementation (libs.accompanist.swiperefresh)
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0") {
        because("androidx.compose.ui:ui-test-junit4:1.7.2 requires 3.5.0")
    }
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.2") {
        exclude(group = "androidx.test.espresso", module = "espresso-core")
    }

    implementation(platform(libs.firebase.bom))
    implementation(libs.google.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.charty)
    implementation (libs.okhttp)
    implementation (libs.firebase.messaging.ktx)
    implementation (libs.androidx.work.runtime.ktx)
    implementation(libs.reorderable)
    implementation(libs.maps.compose)
}
configurations.all {
    resolutionStrategy {
        force("androidx.test.espresso:espresso-core:3.5.0")
    }
}
