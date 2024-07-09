import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.woojun.adego"
    compileSdk = 34

    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { localProperties.load(it) }
    }

    val KAKAO_NATIVE_KEY = localProperties.getProperty("KAKAO_NATIVE_KEY") ?: ""
    val MANIFESTS_KAKAO_NATIVE_KEY = localProperties.getProperty("MANIFESTS_KAKAO_NATIVE_KEY") ?: ""
    val MAPS_API_KEY = localProperties.getProperty("MAPS_API_KEY") ?: ""
    val REST_API_KEY = localProperties.getProperty("REST_API_KEY") ?: ""
    val BASE_URL = localProperties.getProperty("BASE_URL") ?: ""

    defaultConfig {
        applicationId = "com.woojun.adego"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "KAKAO_NATIVE_KEY", "\"$KAKAO_NATIVE_KEY\"")
        resValue("string", "KAKAO_NATIVE_KEY", KAKAO_NATIVE_KEY)

        buildConfigField("String", "MANIFESTS_KAKAO_NATIVE_KEY", "\"$MANIFESTS_KAKAO_NATIVE_KEY\"")
        resValue("string", "MANIFESTS_KAKAO_NATIVE_KEY", MANIFESTS_KAKAO_NATIVE_KEY)

        buildConfigField("String", "MAPS_API_KEY", "\"$MAPS_API_KEY\"")
        resValue("string", "MAPS_API_KEY", MAPS_API_KEY)

        buildConfigField("String", "REST_API_KEY", "\"$REST_API_KEY\"")
        resValue("string", "REST_API_KEY", REST_API_KEY)

        buildConfigField("String", "BASE_URL", "\"$BASE_URL\"")
        resValue("string", "BASE_URL", BASE_URL)

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
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.kakao.sdk:v2-all:2.20.0")

    implementation(platform("com.google.firebase:firebase-bom:32.7.3"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    implementation("com.github.prolificinteractive:material-calendarview:2.0.1")

    implementation("com.jakewharton.threetenabp:threetenabp:1.3.1")

    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}