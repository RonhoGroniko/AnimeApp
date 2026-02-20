plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.apollo)
}

apollo {
    service("service") {
        packageName.set("schema")
        generateApolloMetadata.set(true)
        introspection {
            endpointUrl.set("https://shikimori.one/api/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}

android {
    namespace = "com.sharapov.core_network"
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
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        freeCompilerArgs.addAll(
            "-Xcontext-parameters",
            "-opt-in=kotlin.RequiresOptIn"
        )
    }
}

dependencies {

    implementation(project(":core-domain"))

    implementation(libs.kotlinx.coroutines.core)
    compileOnly(libs.javax.inject)

    implementation(libs.kotlinx.serialization.json)

    api(libs.apollo.runtime)

    api(libs.androidx.paging.runtime)
    api(libs.androidx.paging.compose)

    implementation(libs.logging.interceptor)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
}