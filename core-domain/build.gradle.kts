plugins {
    alias(libs.plugins.android.library)  
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.sharapov.core_domain"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" }
    }
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        freeCompilerArgs.set(listOf(
            "-Xcontext-parameters",
            "-opt-in=kotlin.RequiresOptIn"
        ))
    }
}

dependencies {

    api(libs.kotlinx.coroutines.core)
    compileOnly(libs.javax.inject)

    testImplementation(libs.junit)
}