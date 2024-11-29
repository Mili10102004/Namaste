plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.namaste"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.namaste"
        minSdk = 24
        targetSdk = 34
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
        debug {
            isDebuggable = true // Added for better debugging support
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true // Enable ViewBinding for easier UI handling
    }
}

dependencies {
    // AndroidX Libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation("com.razorpay:checkout:1.6.21")

    // Firebase Libraries - Using Firebase BOM to manage versions
    implementation(platform("com.google.firebase:firebase-bom:32.0.0")) // Firebase BOM for version management
    implementation("com.google.firebase:firebase-database") // Firebase Realtime Database
    implementation("com.google.firebase:firebase-firestore") // Firestore integration
    implementation("com.google.firebase:firebase-analytics") // Firebase Analytics

    // Testing Libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Additional Dependencies (if required)
    implementation("com.squareup.okhttp3:okhttp:4.11.0") // For network requests
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0") // Kotlin standard library


}
