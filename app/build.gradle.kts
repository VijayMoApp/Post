plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "dev.vijayakumar.adminapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.vijayakumar.adminapp"
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.material3)
    implementation (libs.androidx.navigation.compose)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation (libs.hilt.android)
    implementation (libs.androidx.hilt.navigation.compose)
    ksp (libs.hilt.compiler)
    implementation (libs.androidx.material)


    //Room
    implementation(libs.room)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    //Coroutines
    implementation (libs.coroutines.android)
    implementation (libs.coroutines.core)

    //Retrofit
    implementation(libs.retrofit)
    implementation (libs.converter.gson)

    // Coil for image loading in Jetpack Compose
    implementation (libs.coil.compose)

    implementation (libs.timber)

    implementation (libs.androidx.datastore.preferences)

    implementation (libs.androidx.material.icons.extended)

    implementation (libs.androidx.foundation)
    implementation (libs.logging.interceptor)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Mockito Core
    testImplementation (libs.mockito.core)
    testImplementation (libs.mockito.inline)

// Kotlin Coroutines Test
    testImplementation (libs.kotlinx.coroutines.test)

    testImplementation (libs.androidx.core.testing)
}