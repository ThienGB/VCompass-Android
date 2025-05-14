plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    id("realm-android")
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.gotravel"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.gotravel"
        minSdk = 24
        targetSdk = 35
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
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
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.compose.testing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation (libs.dagger)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    implementation (libs.picasso)
    implementation(libs.coil.compose)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
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

    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation(libs.charty)
    implementation(libs.cloudinary.android)
    implementation (libs.retrofit.v290)
    implementation (libs.converter.gson.v290)
    implementation (libs.okhttp)
    implementation ("com.google.firebase:firebase-messaging-ktx:24.1.1")
    implementation ("androidx.work:work-runtime-ktx:2.9.1")
    implementation ("androidx.datastore:datastore-preferences:1.1.6")
    implementation("org.burnoutcrew.composereorderable:reorderable:0.9.6")
    implementation("com.google.maps.android:maps-compose:6.4.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}
configurations.all {
    resolutionStrategy {
        force("androidx.test.espresso:espresso-core:3.5.0")
    }
}
