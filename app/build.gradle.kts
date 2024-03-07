import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.woojun.adego"
    compileSdk = 34

    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { localProperties.load(it) }
    }

    val NATIVE_APP_KEY = localProperties.getProperty("NATIVE_APP_KEY") ?: ""
    val MANIFESTS_KAKAO_NATIVE_KEY = localProperties.getProperty("MANIFESTS_KAKAO_NATIVE_KEY") ?: ""

    defaultConfig {
        applicationId = "com.woojun.adego"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "NATIVE_APP_KEY", "\"$NATIVE_APP_KEY\"")
        resValue("string", "NATIVE_APP_KEY", NATIVE_APP_KEY)

        buildConfigField("String", "MANIFESTS_KAKAO_NATIVE_KEY", "\"$MANIFESTS_KAKAO_NATIVE_KEY\"")
        resValue("string", "MANIFESTS_KAKAO_NATIVE_KEY", MANIFESTS_KAKAO_NATIVE_KEY)

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.github.bumptech.glide:glide:4.16.0")
}