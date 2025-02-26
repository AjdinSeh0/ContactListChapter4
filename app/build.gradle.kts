plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mycontactlist"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mycontactlist"
        minSdk = 21
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // ✅ Correct Play Services dependencies for Google Maps
    implementation(libs.play.services.location)  // Location services for GPS tracking
    implementation(libs.play.services.maps)  // Google Maps SDK
    implementation(libs.play.services.auth)  // Google Sign-In (if needed)

    // ✅ Exclude old Android support library to avoid duplicate class errors
    implementation(libs.play.services.maps) {
        exclude(group = "com.android.support")
    }

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
