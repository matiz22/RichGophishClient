import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization") version "1.9.21"
    alias(libs.plugins.yshrsmz.buildKonfig)
    alias(libs.plugins.libres)
}

buildkonfig {
    packageName = "com.matiz22.richgophishclient"

    defaultConfigs {
        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "MAIN_API_KEY", ((gradleLocalProperties(rootDir).getProperty("main.api.key") ?: ""))
        )
        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "MAIN_API_HOST", ((gradleLocalProperties(rootDir).getProperty("main.api.host") ?: ""))
        )
        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "OLLAMA_API_HOST", ((gradleLocalProperties(rootDir).getProperty("ollama.api.host") ?: ""))
        )
        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "OLLAMA_EMAIL_MODEL", ((gradleLocalProperties(rootDir).getProperty("ollama.email.model") ?: ""))
        )
        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "OLLAMA_PAGE_MODEL", ((gradleLocalProperties(rootDir).getProperty("ollama.page.model") ?: ""))
        )
    }

}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.koin.core)
            implementation(libs.libres)
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
        }
        jvmMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.swing)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        macosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.matiz22.richgophishclient.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
libres {
    generatedClassName = "SharedRes"
    generateNamedArguments = true
    baseLocaleLanguageCode = "en"
}

