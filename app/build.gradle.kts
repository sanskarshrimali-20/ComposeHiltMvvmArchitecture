plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    id("kotlinx-serialization")

}

android {
    namespace = "com.newcompose.composehiltmvvm"
    compileSdk = libs.versions.compile.sdk.version.get().toInt()

    defaultConfig {
        applicationId = "com.newcompose.composehiltmvvm"
        minSdk = libs.versions.min.sdk.version.get().toInt()
        targetSdk = libs.versions.target.sdk.version.get().toInt()
        versionCode = libs.versions.version.code.get().toInt()
        versionName = libs.versions.version.name.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources =  false

            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "BASE_IMG_URL", "\"https://image.tmdb.org/t/p/w400/\"")
        }

        release {
            isMinifyEnabled =  false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "BASE_IMG_URL", "\"https://image.tmdb.org/t/p/w400/\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packaging {
        resources.excludes.apply {
            add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

    kotlin {
        sourceSets {
            all {
                kotlinOptions {
                    freeCompilerArgs += "-Xverbose"
                }
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.compose.fundation)
    implementation(libs.compose.preview)
    implementation(libs.compose.activity)

    // Lifecycle
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.androidx.compiler)
    implementation(libs.hilt.navigation.compose)

    // Gson
    implementation(libs.gson)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // OkHttp
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Accompanist
    implementation(libs.accompanist.permission)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.compose.junit.ui)
    debugImplementation(libs.compose.ui.tooling.debug)
    debugImplementation(libs.compose.ui.test.manifest)

    implementation("androidx.compose.animation:animation:1.5.1")

    // Retrofit
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.1.0")

    // Accompanist
    implementation("com.google.accompanist:accompanist-navigation-animation:0.24.4-alpha")


    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}