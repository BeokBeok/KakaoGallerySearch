plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.beok.kakaogallerysearch"
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {

    AndroidX.run {
        implementation(CORE_KTX)
        implementation(APPCOMPAT)
        implementation(CONSTRAINT_LAYOUT)
        implementation(FRAGMENT_KTX)
        implementation(LIFECYCLE_RUNTIME_KTX)
        testImplementation(CORE_TESTING)
    }

    implementation(Google.MATERIAL)

    Network.run {
        implementation(RETROFIT)
        implementation(CONVERTER_MOSHI)
    }

    Moshi.run {
        implementation(KOTLIN)
        implementation(ADAPTERS)
        implementation(KOTLIN_CODEGEN)
    }

    Coroutines.run {
        implementation(CORE)
        testImplementation(TEST)
    }

    Hilt.run {
        implementation(ANDROID)
        kapt(COMPILER)
    }

    Glide.run {
        implementation(CORE)
        kapt(COMPILER)
    }

    Stetho.run {
        implementation(CORE)
        implementation(OKHTTP)
    }

    testImplementation(JUnit.CORE)
    testImplementation(Mock.K)
    testImplementation(Etc.TURBINE)
    coreLibraryDesugaring(DESUGAR.JDK_LIBS)
}
