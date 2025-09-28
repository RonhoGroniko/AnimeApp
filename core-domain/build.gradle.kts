plugins {
    kotlin("jvm")
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

    api(libs.kotlinx.coroutines.core)
    compileOnly(libs.javax.inject)

    testImplementation(libs.junit)
}