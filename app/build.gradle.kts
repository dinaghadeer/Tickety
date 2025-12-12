plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.example.tickety"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.tickety"
        minSdk = 24
        targetSdk = 36
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation("androidx.navigation:navigation-compose:2.7.2")
    implementation("androidx.compose.material:material-icons-extended:<compose-version>")  // for icons

    // Room Database
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version") // For Kotlin Coroutines support
    ksp("androidx.room:room-compiler:$room_version") // Make sure you are using ksp or kapt

    //view model dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //Junit ,Mockito
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    
        // UI dependencies
        androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.0") // تأكدي من توافق النسخة
        debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.0")

        // مكتبات Mockito لعمل النسخ الوهمية
        androidTestImplementation("org.mockito:mockito-android:5.3.1")
        androidTestImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")



        // 1. مكتبات الـ Unit Testing (تعمل على الكمبيوتر - سريعة)
        testImplementation("junit:junit:4.13.2")
        testImplementation("org.mockito:mockito-core:5.7.0")
        testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

        // 2. مكتبات الـ Instrumented Testing (تعمل على الموبايل/Emulator - تحتاج Context)
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation("androidx.room:room-testing:2.6.1") // ضروري لاختبار Room


}