plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    // Firebase Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.everyn"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.everyn"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures{
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Firebase BOM — downgraded to be compatible with Kotlin 1.9.0
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))

    // Firebase Auth
        implementation("com.google.firebase:firebase-auth")

    // Google Sign-In — downgraded to be compatible with Kotlin 1.9.0
        implementation("com.google.android.gms:play-services-auth:20.7.0")
}